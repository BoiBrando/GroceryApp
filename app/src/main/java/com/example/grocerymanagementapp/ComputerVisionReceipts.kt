//package com.example.grocerymanagementapp
//
//import android.graphics.Bitmap
//import android.widget.Toast
//import com.google.mlkit.vision.barcode.BarcodeScannerOptions
//import com.google.mlkit.vision.barcode.BarcodeScanning
//import com.google.mlkit.vision.barcode.common.Barcode
//import com.google.mlkit.vision.common.InputImage
//import com.google.mlkit.vision.objects.defaults.ObjectDetectorOptions

//class ComputerVisionReceipts (var bm : Bitmap){



//    val options = BarcodeScannerOptions.Builder()
//        .setBarcodeFormats(Barcode.FORMAT_CODE_128)
//        .build()

//    val image = InputImage.fromBitmap(bm, 0)

//    val scanner = BarcodeScanning.getClient(options)

//    val result = scanner.process(image)
//        .addOnSuccessListener { barcodes ->
//            for (barcode in barcodes) {
//                if(barcode.valueType == Barcode.FORMAT_CODE_128) {
//                barcode.
//                Toast.makeText(requireContext(), barcode.rawValue, Toast.LENGTH_SHORT).show()


//                }
//
//            }
//
//        }.addOnCompleteListener {
////        Toast.makeText(this, "The thing happened", Toast.LENGTH_SHORT).show()
//        }.addOnFailureListener {
////        Toast.makeText(this, "The thing did not happen", Toast.LENGTH_SHORT).show()
//        }.addOnCanceledListener {
////        Toast.makeText(this, "nope", Toast.LENGTH_SHORT).show()
//        }
//}