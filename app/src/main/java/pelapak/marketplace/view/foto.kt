package pelapak.marketplace.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_foto.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import pelapak.marketplace.BuildConfig
import pelapak.marketplace.R
import pelapak.marketplace.persenter._pelapak
import pelapak.marketplace.utils.FileCompressor
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class foto : AppCompatActivity() {
    val REQUEST_TAKE_PHOTO = 1
    val REQUEST_GALLERY_PHOTO = 2
    lateinit var mPhotoFile: File
    var mCompressor: FileCompressor? = null
    lateinit var persenter: _pelapak
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_foto)
        mCompressor = FileCompressor(this)
        persenter = _pelapak()
        iv_back_foto.setOnClickListener { finish() }
        bt_tambah_foto_camera.setOnClickListener {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                        != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                    ),
                    99
                )
            } else {
                dispatchTakePictureIntent()

            }

        }
        bt_tambah_foto_galeri.setOnClickListener {
            if ((ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                        != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.CAMERA
                )
                        != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA
                    ),
                    99
                )
            } else {
                dispatchGalleryIntent()
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode === Activity.RESULT_OK) {
            if (requestCode === REQUEST_TAKE_PHOTO) {
                try {
                    mPhotoFile = mCompressor!!.compressToFile(mPhotoFile)
                    Glide.with(this).load(mPhotoFile).apply(
                        RequestOptions().centerCrop().circleCrop()
                    ).into(iv_foto_foto)
                    persenter.upload(this,this,mPhotoFile,iv_foto_foto)

                } catch (e: IOException) {
                    e.printStackTrace()
                }
            } else if (requestCode === REQUEST_GALLERY_PHOTO) {
                val selectedImage = data!!.data
                try {
                    mPhotoFile = mCompressor!!.compressToFile(File(getRealPathFromUri(selectedImage)))
                    persenter.upload(this,this,mPhotoFile,iv_foto_foto)

                    Glide.with(this).load(mPhotoFile).apply(
                        RequestOptions().centerCrop().circleCrop()
                    ).into(iv_foto_foto)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
    }
    /**
     * Capture image from camera
     */
    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            // Create the File where the photo should go
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
            } catch (ex: IOException) {
                ex.printStackTrace()
                // Error occurred while creating the File
            }
            if (photoFile != null) {
                val photoURI = FileProvider.getUriForFile(
                    this, BuildConfig.APPLICATION_ID.toString() + ".provider",
                    photoFile
                )
                mPhotoFile = photoFile
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
            }
        }
    }


    /**
     * Select image fro gallery
     */
    private fun dispatchGalleryIntent() {
        val pickPhoto = Intent(
            Intent.ACTION_PICK,
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        pickPhoto.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivityForResult(pickPhoto, REQUEST_GALLERY_PHOTO)
    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMddHHmmss").format(Date())
        val mFileName = "JPEG_" + timeStamp + "_"
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(mFileName, ".jpg", storageDir)
    }


    /**
     * Get real file path from URI
     *
     * @param contentUri
     * @return
     */
    fun getRealPathFromUri(contentUri: Uri?): String? {
        var cursor: Cursor? = null
        return try {
            val proj =
                arrayOf(MediaStore.Images.Media.DATA)
            cursor = contentUri?.let { contentResolver.query(it, proj, null, null, null) }
            assert(cursor != null)
            val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            cursor!!.moveToFirst()
            cursor!!.getString(column_index)
        } finally {
            if (cursor != null) {
                cursor.close()
            }
        }
    }
}
