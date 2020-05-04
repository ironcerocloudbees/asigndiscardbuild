package com.cloudbees

def call(object, method){
    return object.metaClass.getMetaMethod(method) != null
}