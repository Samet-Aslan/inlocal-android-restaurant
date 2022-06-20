package com.inlocal.restaurantapp.easyphotopicker

/**
 * Stas Parshin
 * 05 November 2015
 */
public abstract class DefaultCallback : EasyImage.Callbacks {

    override fun onImagePickerError(error: Throwable, source: MediaSource) {}

    override fun onCanceled(source: MediaSource) {}
}
