use rustfft::num_traits::Float;
use pitchdetector::detector::autocorrelation::AutocorrelationDetector;
use pitchdetector::detector::mcleod::McLeodDetector;
use pitchdetector::detector::PitchDetector;
use pitchdetector::utils::buffer::new_real_buffer;

pub(crate) fn detect_pitch_ii16(mut data: Vec<i16>) {
    let signal: Vec<f32> = data.iter()
        .map(|x| f32::from(*x))
        .collect();
    detect_pitch(signal);
}

pub(crate) fn detect_pitch(mut data: Vec<f32>) {
    let signal: Vec<f64> = data.iter()
        .map(|x| f64::from(*x))
        .collect();

    const SAMPLE_RATE: usize = 44100;
    let duration: f64 = data.len() as f64 / SAMPLE_RATE as f64;
    let sample_size: usize = (SAMPLE_RATE as f64 * duration) as usize;
    const WINDOW: usize = 1024;
    const PADDING: usize = WINDOW / 2;
    const DELTA_T: usize = WINDOW / 4;
    const POWER_THRESHOLD: f64 = 300.0;
    const CLARITY_THRESHOLD: f64 = 0.6;
    let n_windows: usize = (sample_size - WINDOW) / DELTA_T;

    let mut chunk = new_real_buffer(WINDOW);

    let mut detector = detector_factory("Autocorrelation".to_string(), WINDOW, PADDING);

    for i in 0..n_windows {
        let t: usize = i * DELTA_T;
        get_chunk(&signal, t, WINDOW, &mut chunk);

        let pitch = detector.get_pitch(&chunk, SAMPLE_RATE, POWER_THRESHOLD, CLARITY_THRESHOLD);

        match pitch {
            Some(pitch) => {
                let frequency = pitch.frequency;
                let clarity = pitch.clarity;
                let idx = SAMPLE_RATE as f64 / frequency;
                let epsilon = (SAMPLE_RATE as f64 / (idx - 1.0)) - frequency;
                println!(
                    "Chosen Peak idx: {}; clarity: {}; freq: {} +/- {}",
                    idx, clarity, frequency, epsilon
                );
                // assert!((frequency - freq_in).abs() < ERROR_TOLERANCE * epsilon);
            }
            None => {
                println!("No peaks accepted.");
            }
        }
    }
}

pub(crate) fn detector_factory(name: String, window: usize, padding: usize) -> Box<dyn PitchDetector<f64>> {
    match name.as_ref() {
        "McLeod" => {
            return Box::new(McLeodDetector::<f64>::new(window, padding));
        }
        "Autocorrelation" => {
            return Box::new(AutocorrelationDetector::<f64>::new(window, padding));
        }
        _ => {
            panic!("Unknown detector {}", name);
        }
    }
}

pub(crate) fn get_chunk<T: Float>(signal: &[T], start: usize, window: usize, output: &mut [T]) {
    let start = match signal.len() > start {
        true => start,
        false => signal.len(),
    };

    let stop = match signal.len() >= start + window {
        true => start + window,
        false => signal.len(),
    };

    for i in 0..stop - start {
        output[i] = signal[start + i];
    }

    for i in stop - start..output.len() {
        output[i] = T::zero();
    }
}