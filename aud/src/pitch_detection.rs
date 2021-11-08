use std::ops::Add;
use rustfft::num_traits::Float;
use crate::detector::autocorrelation::AutocorrelationDetector;
use crate::detector::mcleod::McLeodDetector;
use crate::detector::PitchDetector;
use crate::utils::buffer::new_real_buffer;


pub fn detect_pitch_ii16(mut data: Vec<i16>) -> String {
    let signal: Vec<f32> = data.iter()
        .map(|x| f32::from(*x))
        .collect();
    return detect_pitch(signal);
}

pub fn detect_pitch(mut data: Vec<f32>) -> String {
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

    let mut pitch_str: String = String::from("Not found");
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

                pitch_str.push_str(";");
                pitch_str.push_str(pitch.frequency.to_string().as_str());

                // assert!((frequency - freq_in).abs() < ERROR_TOLERANCE * epsilon);
            }
            None => {
                println!("No peaks accepted.");
            }
        }
    }
    return pitch_str;
}

pub fn detector_factory(name: String, window: usize, padding: usize) -> Box<dyn PitchDetector<f64>> {
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

pub fn get_chunk<T: Float>(signal: &[T], start: usize, window: usize, output: &mut [T]) {
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