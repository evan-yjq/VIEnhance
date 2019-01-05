LOCAL_PATH := $(call my-dir)
include $(CLEAR_VARS)

OpenCV_INSTALL_MODULES:=on
OPENCV_CAMERA_MODULES:=on
OPENCV_LIB_TYPE:=SHARED

ifeq ("$(wildcard $(OPENCV_MK_PATH))","")
include C:\Users\yjq87\Downloads\opencv-contrib-android-sdk\sdk\native\jni\OpenCV.mk
else
include $(OPENCV_MK_PATH)
endif

LOCAL_MODULE := face
LOCAL_SRC_FILES := FindFace.cpp

LOCAL_LDLIBS += -L$(SYSROOT)/usr/lib

include $(BUILD_SHARED_LIBRARY)