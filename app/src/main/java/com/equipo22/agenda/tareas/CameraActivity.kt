package com.equipo22.agenda.tareas

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Size
import android.view.ViewGroup
import android.widget.Toast
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import com.equipo22.agenda.databinding.ActivityCameraBinding
import java.io.File
import java.util.concurrent.Executors

class CameraActivity : AppCompatActivity() {

    private  lateinit var binding: ActivityCameraBinding

    val PERMISSION_ID = 34

    private val executor = Executors.newSingleThreadExecutor()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if(checkCameraPermission()){
            startCamera()
        } else{
            requestPermissions()
            binding.captureButton.setOnClickListener {
                requestPermissions()
            }
        }
    }

    fun startCamera(){

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetResolution(Size(640, 480)) //resolucción
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {

            val parent =  binding.parent as ViewGroup
            parent.removeView(binding.cameraPreview)
            parent.addView(binding.cameraPreview, 0)

            binding.cameraPreview.setSurfaceTexture(it.surfaceTexture)

        }

        val imageCapture = captureConfig()

        CameraX.bindToLifecycle(this, preview,imageCapture)
    }


    fun captureConfig(): ImageCapture {
        val imageCaptureConfig = ImageCaptureConfig.Builder()
            .apply {
                setCaptureMode(ImageCapture.CaptureMode.MIN_LATENCY)
            }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)

        binding.captureButton.setOnClickListener {
            val file = File(externalMediaDirs.first(),
                "${System.currentTimeMillis()}.jpg")

            imageCapture.takePicture(file, executor,
                object : ImageCapture.OnImageSavedListener {

                    override fun onError(
                        imageCaptureError: ImageCapture.ImageCaptureError,
                        message: String,
                        exc: Throwable?
                    ) {
                        val msg = "Photo capture failed: $message"
                        Log.e("CameraXApp", msg, exc)
                        binding.cameraPreview.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onImageSaved(file: File) {
                        val msg = "¡Imagen guardada!: ${file.absolutePath}"
                        Log.d("Camera", msg)
                        binding.cameraPreview.post {
                            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                        }
                        onBackPressed()

                    }
                })
        }

        return imageCapture
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_ID) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                startCamera()
            }
        }
    }

    private fun checkCameraPermission(): Boolean{
        return ActivityCompat
            .checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA, Manifest.permission.ACCESS_FINE_LOCATION),
            PERMISSION_ID
        )
    }

    override fun onBackPressed() {
        val intent = Intent(this, TareaManagementActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }
}