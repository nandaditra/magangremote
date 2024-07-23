package com.example.magangremote.ui.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.magangremote.R
import com.example.magangremote.auth.UserPreferences
import com.example.magangremote.databinding.ActivityProfileBinding
import com.example.magangremote.model.User
import com.example.magangremote.ui.auth.AuthActivity
import com.example.magangremote.ui.home.HomeActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var storageRef: FirebaseStorage
    private lateinit var firebaseStore:FirebaseFirestore
    private lateinit var user:FirebaseUser
    private lateinit var dataPreferences: UserPreferences
    private var currentImageUri: Uri? = null
    private var newImg:Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = Firebase.auth
        storageRef = FirebaseStorage.getInstance()
        firebaseStore = FirebaseFirestore.getInstance()
        user = firebaseAuth.currentUser!!
        dataPreferences = UserPreferences(this)

        var userId = firebaseAuth.currentUser?.uid

        val profileRef = storageRef.reference.child("user/"+firebaseAuth.currentUser?.uid+"/profile.jpg"+"")
        profileRef.downloadUrl.addOnSuccessListener{ task ->
            if(task != null) {
                Glide.with(this@ProfileActivity)
                    .load(task)
                    .apply(RequestOptions().override(1000,1000))
                    .into(binding.imageProfile)
            } else {
                Glide.with(this@ProfileActivity)
                    .load("https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png")
                    .apply(RequestOptions().override(1000,1000))
                    .into(binding.imageProfile)
            }
        }

        actionBar?.setDisplayHomeAsUpEnabled(true)

        binding.apply {
            imageProfile.setOnClickListener { startGallery() }
            btnSave.setOnClickListener {
                val name = inputName.text.toString().trim()
                val email = inputEmail.text.toString().trim()
                val nomorHandphone = inputHandphone.text.toString().trim()
                val interest = inputInterest.text.toString().trim()

                binding.progressBar.visibility = View.VISIBLE
                user.updateEmail(email).addOnSuccessListener{task ->
                    var docRef = firebaseStore.collection("user").document(user.uid)
                    val user :HashMap<String, Any> = HashMap<String, Any>()
                    user.put("name", name);
                    user.put("email", email);
                    user.put("handphoneNumber", nomorHandphone);
                    user.put("interest", interest);

                    docRef.update(user)
                    binding.progressBar.visibility = View.INVISIBLE
                    startActivity(Intent(this@ProfileActivity, HomeActivity::class.java))
                }
            }
            btnLogut.setOnClickListener { logout() }
            btnCancel.setOnClickListener {
                val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
                startActivity(intent)
            }
        }

        showBiodataProfile(userId)
    }

    private fun showBiodataProfile(userId:String?){
        binding.progressBar.visibility = View.VISIBLE
        val documentReference =  firebaseStore.collection("user").document(userId.toString())
        documentReference.addSnapshotListener{snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            if (snapshot != null && snapshot.exists()) {
                binding.apply {
                    progressBar.visibility = View.INVISIBLE
                    inputName.setText(snapshot.getString("name"))
                    inputEmail.setText(snapshot.getString("email"))
                    inputHandphone.setText(snapshot.getString("handphoneNumber"))
                    inputInterest.setText(snapshot.getString("interest"))
                }
                if(snapshot.getString("imageUrl") != null) {
                    Glide.with(this@ProfileActivity)
                        .load(snapshot.getString("imageUrl"))
                        .apply(RequestOptions().override(1000,1000))
                        .into(binding.imageProfile)
                } else {
                    Glide.with(this@ProfileActivity)
                        .load("https://exoffender.org/wp-content/uploads/2016/09/empty-profile.png")
                        .apply(RequestOptions().override(1000,1000))
                        .into(binding.imageProfile)
                }
            }
        }
    }


    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("Image URI", "showImage: $it")
            uploadImageToFirebase(it)
        }
    }

    private fun startGallery() { launcherGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)) }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 1000) {
            if(resultCode == Activity.RESULT_OK) {
                val imageUri = data?.data
                if (imageUri != null) {
                    uploadImageToFirebase(imageUri)
                }
            }
        }
    }

    private fun uploadImageToFirebase(imageUri: Uri) {
              binding.progressBar.visibility = View.VISIBLE
              val ref = storageRef.reference.child("user/"+firebaseAuth.currentUser?.uid+"/profile.jpg"+"")
              ref.putFile(imageUri).addOnSuccessListener { taskSnapshot ->
                  ref.downloadUrl.addOnSuccessListener{ task ->
                      binding.progressBar.visibility = View.INVISIBLE
                      if(task != null) {
                         newImg = task
                          Glide.with(this@ProfileActivity)
                              .load(task)
                              .apply(RequestOptions().override(1000,1000))
                              .into(binding.imageProfile)
                      }
                      Toast.makeText(this@ProfileActivity, "Photo Successfully Changes", Toast.LENGTH_SHORT).show()
                  }
              }.addOnFailureListener {
                  Toast.makeText(this@ProfileActivity, "Photo not changes", Toast.LENGTH_SHORT).show()
              }
    }

    private fun logout(){
        dataPreferences.clear()
        startActivity(Intent(this@ProfileActivity, AuthActivity::class.java))
        Toast.makeText(this@ProfileActivity, "You log out", Toast.LENGTH_SHORT).show()
        finish()
    }
}