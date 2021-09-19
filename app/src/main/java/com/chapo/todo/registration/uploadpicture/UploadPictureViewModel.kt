package com.chapo.todo.registration.uploadpicture

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UploadPictureViewModel @Inject constructor(

) : ViewModel() {

    private val _uploadPictureState = MutableLiveData<UploadPictureState>()
    val uploadPictureState: LiveData<UploadPictureState> = _uploadPictureState

    fun uploadUserPicture(pictureUri: Uri?) {
        if (pictureUri == null) {
            _uploadPictureState.value = UploadPictureError("Please choose image")
        } else {
            _uploadPictureState.value = UploadPictureSuccess
        }
    }
}

sealed class UploadPictureState
object UploadPictureSuccess : UploadPictureState()
data class UploadPictureError(val error: String) : UploadPictureState()