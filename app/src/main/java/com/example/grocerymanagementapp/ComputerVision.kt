package com.example.grocerymanagementapp

import android.content.Context
import android.graphics.Bitmap
import com.example.grocerymanagementapp.ml.ModelUnquant
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.nio.ByteBuffer
import java.nio.ByteOrder

class ComputerVision {

    fun classifyImage(image : Bitmap, imageSize : Int, cont : Context) : String {
        val model = ModelUnquant.newInstance(cont)

        // Creates inputs for reference.
        val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
        val byteBuffer : ByteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
        byteBuffer.order(ByteOrder.nativeOrder())

        val intValues = IntArray(imageSize * imageSize)
        image.getPixels(intValues, 0, image.width, 0, 0, image.width, image.height)

        var pixel = 0

        // Make sure this is still working:
        // The lines must keep going with
        // repeat() instead of for()
        repeat(imageSize) {
            repeat(imageSize) {
                val valueThing = intValues[pixel++] // RGB
                byteBuffer.putFloat(((valueThing shr 16) and 0xFF) * (1.0f / 1))
                byteBuffer.putFloat(((valueThing shr 8) and 0xFF) * (1.0f / 1))
                byteBuffer.putFloat((valueThing and 0xFF) * (1.0f / 1))
            }
        }

        inputFeature0.loadBuffer(byteBuffer)

        // Runs model inference and gets result.
        val outputs = model.process(inputFeature0)
        val outputFeature0 = outputs.outputFeature0AsTensorBuffer

        val confidences = outputFeature0.floatArray

        // Find the index of the class with the biggest confidence
        var maxPos = 0
        var maxConfidence = 0f

        // Make sure this works.
        // Replaced confidences.size
        // with confidences.incices
        for(i in confidences.indices) {
            if(confidences[i] > maxConfidence) {
                maxConfidence = confidences[i]
                maxPos = i
            }
        }

        val classes = arrayOf<String>("Apple", "Orange")

        // Releases model resources if no longer used.
        model.close()

        return classes[maxPos]
    }



}