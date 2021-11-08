use std::mem::size_of_val;
use std::ptr::null;
pub use detector::internals::Pitch;
use crate::pitch_detection::detect_pitch_ii16;

use jni::JNIEnv;
use jni::objects::{JClass, JObject};
use jni::objects::ReleaseMode::{CopyBack, NoCopyBack};
use jni::sys::{jintArray, jobject, jshortArray, jstring};

pub mod detector;
pub mod float;
pub mod utils;
pub mod pitch_detection;

#[no_mangle]
pub extern fn double_input(input: i32) -> i32 {
    println!("rejuvenating");
    input * 2
}

#[no_mangle]
pub extern fn detect_pitch(env: JNIEnv, _: JClass, array: jshortArray) -> String {
    let size = size_of_val(&array);

    println!("hello88 {}", size);
    env.exception_check();
    println!("hello99 {}", size);

    // println!({}, size);
    println!("hello {}", size);

    let blen = env.get_array_length(array).unwrap() as usize;
    println!("hello4 {}", blen);
    let test = env.get_array_length(array);
    println!("hello3");
    let test = env.get_short_array_elements(array, NoCopyBack);
    println!("hello2");
    let mut vec: Vec<i16> = vec![];

    for x in test {
        unsafe { vec.push(x.as_ptr().read() as i16); }
    }
    return detect_pitch_ii16(vec);
    // return JavaString::from_rust(&env, "hhh").unwrap();
}

#[no_mangle]
#[allow(non_snake_case)]
pub extern "system" fn Java_com_picis_piano_AudioLibrary_doStuffInNative(
    env: JNIEnv,
    _class: JClass,
    array: jobject
) -> String {
    let size = size_of_val(&array);

    println!("hello88 {}", size);
    env.exception_check();
    println!("hello99 {}", size);

    let class = env.get_object_class(array);
    let size2 = size_of_val(&class);

    // println!({}, size);
    println!("hello {}", size);
    println!("hello333 {}", size2);

    let blen = env.get_array_length(array).unwrap() as usize;
    println!("hello4 {}", blen);

    return String::from("fdfd");
    let class = env
        .find_class("com/picis/piano/AudioLibrary")
        .expect("Failed to load the target class");
    let result = env.call_static_method(class, "callback", "()V", &[]);
    //
    // result.map_err(|e| e.to_string()).unwrap();
}
