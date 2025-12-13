package com.example.securitydevicesassetmgmtandroid.Services;

import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class AuthFirebase {
    private FirebaseAuth mAuth;

    public AuthFirebase(){
        mAuth = FirebaseAuth.getInstance();
    }
    public void signUpUser(String email, String password, Activity activity){
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(
                activity, result -> {
                    if(result.isSuccessful()){
                        Toast.makeText(activity, "Registration is Successful!", Toast.LENGTH_LONG).show();
                        //UserFirebase newUser = new UserFirebase(mAuth.getCurrentUser().getUid(), email);
                        //FirebaseService fbDatabase = new FirebaseService();
                        //fbDatabase.addUser(newUser);
                    }
                    else{
                        Toast.makeText(activity, "Registration Failed! " + result.getException().getMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public interface AuthResultCallback{
        void onComplete(boolean isSuccess);
    }

    public void signInUser(String email, String password, Activity activity, AuthResultCallback callback){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                activity, result -> {
                    if(result.isSuccessful()){
                        Toast.makeText(activity, "Login is Successful!", Toast.LENGTH_LONG).show();
                        callback.onComplete(true);
                    }
                    else{
                        Toast.makeText(activity, "Login Failed!" + result.getException().getMessage(), Toast.LENGTH_LONG).show();
                        callback.onComplete(false);
                    }
                }
        );
    }
    public FirebaseUser getCurrentUser(){
        return mAuth.getCurrentUser();
    }

    public void getIdToken(TokenCallback callback) {
        FirebaseUser user = getCurrentUser();
        if (user != null) {
            user.getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                @Override
                public void onComplete(Task<GetTokenResult> task) {
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();
                        callback.onTokenReceived(token);
                    } else {
                        callback.onTokenReceived(null);
                    }
                }
            });
        } else {
            callback.onTokenReceived(null);
        }
    }

    public interface TokenCallback {
        void onTokenReceived(String token);
    }


}
