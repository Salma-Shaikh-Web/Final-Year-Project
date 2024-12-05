package com.example.hajricard.utils;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseUtil {

     public static boolean isloggedin(){
        if (currentUserId()!=null){
            return true;
        }
        return false;
    }
    public static String currentUserId(){

        return FirebaseAuth.getInstance().getUid();
    }

    public static DocumentReference getCurrentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static CollectionReference allUserCollectionReference(){
         return FirebaseFirestore.getInstance().collection("users");
    }
}
