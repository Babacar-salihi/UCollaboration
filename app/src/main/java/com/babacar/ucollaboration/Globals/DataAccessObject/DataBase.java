package com.babacar.ucollaboration.Globals.DataAccessObject;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.babacar.ucollaboration.Globals.Models.Etudiant;
import com.babacar.ucollaboration.Globals.Utilitaires.PhotoUtilitaire;
import com.babacar.ucollaboration.UMaps.Models.Lieu;
import com.babacar.ucollaboration.UMarket.Adapters.RecyclerViewBien;
import com.babacar.ucollaboration.UMarket.Modeles.Bien;
import com.babacar.ucollaboration.UMarket.Modeles.DetailsPrestation;
import com.babacar.ucollaboration.UMarket.Modeles.ImageBien;
import com.babacar.ucollaboration.UMarket.Modeles.Panier;
import com.babacar.ucollaboration.UService.Models.Bosseur;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class DataBase {

    //La rachine dans Firebase Real time: nom de l'application.
    public static DatabaseReference sReference = FirebaseDatabase.getInstance().getReference().child("SaleOnLine");

    // Pour les biens: Biens.
    private static StorageReference sStorageRef = FirebaseStorage.getInstance().getReference().child("Biens");
    private static String key = sReference.push().push().getKey();

    // Pour les Utilisateurs: Utilisateurs.
    public static StorageReference sUserProfile = FirebaseStorage.getInstance().getReference().child("Etudiants");
    public static Etudiant sCurrentUser;
    public static Etudiant sAcheteur;

    public static boolean sConnexTest; // Connexion reussi ou pas.
    public static boolean sInscriptTest; // Connexion reussi ou pas.



    // ===================================== PARTIE BIEN ==========================================================

    // ===================================== PUBLICATION BIEN ==========================================================
    /**
     * Permet de publier un bien(Le mettre dans FirebaseDatabase et les photos dans FirebaseStorage).
     * @param context
     * @param bitmap
     * @param bien
     * @return
     */
    public static Boolean publierBien(final Context context, final List<Bitmap> bitmap, final Bien bien) {
        Log.d("KEYYY", key);
        //sPUBTest = false;
        final ProgressDialog progressDialog = new ProgressDialog(context);

        final Iterator<Bitmap> bitmapIterator = bitmap.iterator();
        final int[] testeImg = {0};
        while (bitmapIterator.hasNext()) {

            final String keyImg = sReference.push().push().getKey();
            final Bitmap bitmap1 = bitmapIterator.next();
            final byte[] photo = PhotoUtilitaire.convertBitmapToByte(bitmap1);

            final UploadTask uploadTask = sStorageRef.child(key).child(keyImg)
                    .putBytes(photo);


            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            /*Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                                @Override
                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {

                                    if (!task.isSuccessful()) {

                                        throw task.getException();
                                    }

                                    String urlDownLoad = sStorageRef.child(key).child(keyImg)
                                            .getDownloadUrl().toString();

                                    return sStorageRef.child(key).child(keyImg).getDownloadUrl();
                                }
                            });*/

                            /*String urlDownload = sStorageRef.child(key).child(keyImg)
                                    .getDownloadUrl().toString();*/

//                            String urlDownload = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

                            // Trés important.
                            sStorageRef.child(key).child(keyImg).getDownloadUrl()
                                    .addOnCompleteListener(new OnCompleteListener<Uri>() {
                                @Override
                                public void onComplete(@NonNull Task<Uri> task) {

                                    ImageBien imageBien = new ImageBien();
                                    imageBien.setIdPhoto(keyImg);
                                    imageBien.setPhoto(task.getResult().toString());
                                    imageBien.setHauteur(bitmap1.getHeight());
                                    imageBien.setLargeur(bitmap1.getWidth());

                                    bien.getImages().add(imageBien);

                                    testeImg[0] += 1;
                                    if(testeImg[0] == bitmap.size()) {

                                        saveInDatabase(bien);
                                    }

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(context, "Erreur de publication", Toast.LENGTH_SHORT).show();
                        }
                    })
            ;
        }

        return true;
    }

    /**
     * Permet de mettre un bien uniquement dans FirebaseDatabase.
     * @param bien
     * @return
     */
    private static boolean saveInDatabase(Bien bien){
        bien.setIdBien(key);
        sReference.child("Biens").child(key).setValue(bien);
        return true;
    }


    /**
     * Permet de mettre à jour le bien courramment manipuler, dans la base de données FRT.
     */
    public static boolean updateBien(Bien currentBien){

        if(currentBien.getNombreBien() >= 0){
            sReference.child("Biens").child(currentBien.getIdBien()).setValue(currentBien);
            return true;
        } else {
            return false;
        }
    }

    // ===================================== FIN Publication ==========================================================
    // ===================================== FIN Partie BIEN ==========================================================

    // ============================================= Partie Utillisateur ============================================
    // ================================================= INSCRIPTION ==========================================

    /**
     * Permet d'inscrire un nouvel utilisateur.
     * @param context
     * @param etudiant
     * @param photo
     */
    public static void creationCompte(final Context context, final Etudiant etudiant, final Uri photo){
        Log.d("mImageUri2", photo+"");

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(etudiant.getEmail(), etudiant.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser user = auth.getCurrentUser();
                            etudiant.setIdEtu(user.getUid());
                            Log.d("InscriptionUserDebut", etudiant.toString());

                            //Plus d'informations dans FB_Authentification.
                            mappingPlusInfosFB_Auth(user, etudiant, photo);
                            // Photo de l'utilisateur dans FB_Storage.
                            mappingFBSg(context, user, etudiant, photo);
                            //user.sendEmailVerification(); // Verification de email.

                            user.sendEmailVerification();
                                    /*.addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {

                                            if (task.isSuccessful()) {

                                                Log.d("EMAILLLLL", "Email envoyé");
                                            } else {

                                                Log.d("EMAILLLLL", "Erreurrr");
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {

                                            Log.d("EmaiLLLL", "ONFAILURE");
                                        }
                                    });*/
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sInscriptTest = false;
                        Toast.makeText(context, "Erreur! FBAuth001", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Permet de mettre plus d'informations dans FB_Authentification.
     * @param user
     * @param etudiant
     * @param photo
     */
    private static void mappingPlusInfosFB_Auth(FirebaseUser user, Etudiant etudiant, Uri photo){

        //Plus d'informations dans FB_Authentification.
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(etudiant.getPrenomEtu()+" "+etudiant.getNomEtu())
                .setPhotoUri(photo)
                .build();

        user.updateProfile(changeRequest);
    }

    /**
     * permet de mettre la photo de l'utilisateur dans FB_Storage.
     * @param context
     * @param user
     * @param etudiant
     * @param photo
     */
    private static void mappingFBSg(final Context context, final FirebaseUser user, final Etudiant etudiant, Uri photo){

        sUserProfile.child(user.getUid())
                .putFile(photo)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        String urlPhoto = sUserProfile.child(user.getUid()).toString();
                        etudiant.setPhoto(urlPhoto);
                        // Informations supplementaires dans FB_RealTime.
                        mappingFBRT(context, user, etudiant);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sInscriptTest = false;
                        Toast.makeText(context, "Erreur! FBSg001", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    /**
     * Permet de mettre les informations supplementaires dans FB_RealTime.
     * @param etudiant
     * @param context
     * @param user
     */
    private static void mappingFBRT(final Context context, FirebaseUser user, final Etudiant etudiant){

        sReference.child("Etudiants").child(user.getUid())
                .setValue(etudiant)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        sInscriptTest = true;
                        Log.d("InscriptionUserFin", etudiant.toString());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sInscriptTest = false;
                        Toast.makeText(context, "Erreur! FBRT001", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // ================================= METHODES D'INSCRIPTION SANS PHOTO DE PROFIL ================================

    /**
     * Permet d'inscrire un nouvel utilisateur.
     * @param context
     * @param etudiant
     */
    public static void creationCompte(final Context context, final Etudiant etudiant){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(etudiant.getEmail(), etudiant.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            final FirebaseUser user = auth.getCurrentUser();
                            etudiant.setIdEtu(user.getUid());
                            //Plus d'informations dans FB_Authentification.
                            mappingPlusInfosFB_Auth(user, etudiant);
                            // Photo de l'utilisateur dans Fb_RealTime.
                            mappingFBRT(context, user, etudiant);
                            user.sendEmailVerification(); // Verification de email.
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sInscriptTest = false;
                        Toast.makeText(context, "Erreur! FBAuth002", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    /**
     * Permet de mettre plus d'informations dans FB_Authentification.
     * @param user
     * @param etudiant
     */
    private static void mappingPlusInfosFB_Auth(FirebaseUser user, Etudiant etudiant){

        //Plus d'informations dans FB_Authentification.
        UserProfileChangeRequest changeRequest = new UserProfileChangeRequest.Builder()
                .setDisplayName(etudiant.getPrenomEtu()+" "+etudiant.getNomEtu())
                .build();
        user.updateProfile(changeRequest);
    }

    // ====================================== FIN METHODES D'INSCRIPTION SANS PROFIL ==============================
    // ============================================ FIN Inscription ========================================

    // ============================================ CONNEXION ========================================

    /**
     * Permet aux utilisateurs de se connecter avec leur email et leur mot se passe.
     * @param email
     * @param pwd
     */
    public static void connexion(final Context context, String email, String pwd){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        Log.d("currentUser123", auth.toString());
        auth.signInWithEmailAndPassword(email, pwd)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser user = auth.getCurrentUser();
                            /*if(user.isEmailVerified()) {
                                getCurrentUser_FB_RTDB(user); // Permet d'obtenir les infos supplementaires de l'utilisateur connecté dans FBRTDatabase.
                            } else {
                                Toast.makeText(context, "Validez votre compte svp!", Toast.LENGTH_LONG).show();
                                Intent verif = new Intent(context, EmailVerification.class);
                                verif.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(verif);
                            }*/
                            getCurrentUser_FB_RTDB(user); // Permet d'obtenir les infos supplementaires de l'utilisateur connecté dans FBRTDatabase.
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        sConnexTest = false;
                        Toast.makeText(context, "Se compte n'exite pas. Veuillez vérifiez les infos saisies ou créer un autre compte!", Toast.LENGTH_LONG).show();
                    }
                });
    }

    /**
     * Permet d'obtenir les infos supplementaires de l'utilisateur connecté.
     */
    private static void getCurrentUser_FB_RTDB(FirebaseUser user){

        DatabaseReference etudiant = sReference.child("Etudiants").child(user.getUid());
        etudiant.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                sCurrentUser = dataSnapshot.getValue(Etudiant.class);
                sConnexTest = true;
                loadPanier(sCurrentUser);
                loadFavorie(sCurrentUser);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Récupérer le panier de l'utilisateur
     */
    public static void loadPanier(Etudiant user){

        DatabaseReference etudiantPanier = sReference.child("Etudiants").child(user.getIdEtu()).child("Panier");
        etudiantPanier.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot panierSnapshot : dataSnapshot.getChildren()) {

                    Panier panier = panierSnapshot.getValue(Panier.class);
                    sCurrentUser.getPanier().add(panier);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });
    }

    /**
     *  Récupérer les biens favories de l'utilisateur
     */
    public static void loadFavorie(Etudiant user){

        DatabaseReference etudiantFavorie = sReference.child("Etudiants").child(user.getIdEtu()).child("Favorie");
        etudiantFavorie.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot favorieSnapshot : dataSnapshot.getChildren()) {

                    String favorie = favorieSnapshot.getValue(String.class);
                    sCurrentUser.getFavorie().add(favorie);
                }
                Log.d("CURRENT_USER1", sCurrentUser.toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    /**
     * Permet de récupérer un mot de passe.
     */
    public static void pwdOublié(final Context context, String email) {

        final FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, "Consulter votre compte de Mail!", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = auth.getCurrentUser();
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, "Cet email n'existe pas!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
    // ========================================= FIN Connexion ===============================================

    /**
     * Mettre à jour les infos d'un utilisateur, utiliser surtout lors de l'ajout dans le panier.
     * @param user
     */
    public static void upDateUserPanier(Etudiant user){
        sReference.child("Etudiants")
                .child(user.getIdEtu())
                .child("panier")
                .setValue(user.getPanier());

    }

    /**
     * Mettre à jour les infos d'un utilisateur, utiliser surtout lors des achats.
     * @param user
     */
    /* public static void upDateUserDetails(String idVendeur,Etudiant user){ // idVendeur + detailsCurrentUser.
        Log.d("VENDEURRRRRR", user.toString());
        sReference.child("Etudiants")
                .child(idVendeur)
                .child("Details Prestations")
                .setValue(user.getDetailsPrestations());
    }*/



    /**
     * Mettre à jour les infos d'un utilisateur, utiliser surtout lors de l'ajout dans les favories.
     * @param user
     */
    public static void upDateUserFavorite(Etudiant user){
        sReference.child("Etudiants")
                .child(user.getIdEtu())
                .child("favorie")
                .setValue(user.getFavorie());

    }

    /**
     * Permet de supprimer le compte de l'utilisateur de la base de données.
     */
    public static void supprimerUser() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.delete();
    }

    /**
     * Permet de deconnecter l'utilisateur.
     */
    public static void deconnexionUser() {

        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.signOut();
    }

    // ================================== Fin Partie Utilisateur ====================================================

    //======================================== DETAILS PRESTATION ===================================================

    public static List<Bien> sBienList = new ArrayList<>();
    public static List<Bien> mBiensNvendu = new ArrayList<>();

    /**
     * Permet de récupérer les biens de la base de données.
     */
    public static void getBien() {

        DataBase.sReference.child("Biens")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sBienList.clear();
                        mBiensNvendu.clear();
                        for(DataSnapshot bienSnapshot : dataSnapshot.getChildren()){
                            Bien bien = bienSnapshot.getValue(Bien.class);
                            Log.d("CurrentBien", bien.toString());
                            sBienList.add(bien); // Liste contenant tous les biens de la base.

                            if(bien.getEtatBien() == 0 && bien.isActiver()) // Si le bien n'est pas encore vendu on l'affiche.
                                mBiensNvendu.add(bien); // Liste contenant les biens toujours disponible.
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private static DatabaseReference refDeatils = sReference.child("Details Prestations");
    public static String keyDetails = refDeatils.push().push().getKey();

    public static void ajouterDetails(DetailsPrestation details) {

        refDeatils.child(details.getIdDetail())
                .setValue(details);
    }

    /**
     * Mettre à jour les infos d'un utilisateur, utiliser surtout lors des achats.
     * @param user
     */
    public static void upDateUserDetails(Etudiant user){
        Log.d("VENDEURRRRRR", user.toString());
        sReference.child("Etudiants")
                .child(user.getIdEtu())
                .child("detailsPrestations")
                .setValue(user.getDetailsPrestations());
    }

    /**
     * Mettre à jour les infos d'un utilisateur, utiliser surtout lors de la génértaion de la facture.
     * @param details
     */
    public static void upDateDetailsPrestation(DetailsPrestation details){
        Log.d("VENDEURRRRRR", details.toString());
        sReference.child("Details Prestations")
                .child(details.getIdDetail())
                .setValue(details);
    }

    /**
     * Permet à jour les infos du vendeur dans le noeud Biens.
     * @param bien
     */
    public static void upDateVendeurInBien(Bien bien) {

        sReference.child("Biens")
                .child(bien.getIdBien())
                .child("vendeur")
                .setValue(bien.getVendeur());
    }

    public static Etudiant vendeur1 = new Etudiant();
    public static short sTestGettingSalesMan = 0; // -1: Failure, 0: default, 1: OK;

    /**
     * Permet de récupérer les infos sur les utilsateurs.
     */
    public static void getUserById(String idVendeur) {
        Log.d("GettingTesteDANSDATA", sTestGettingSalesMan+"");
        sReference.child("Etudiants")
                .child(idVendeur)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        vendeur1 = dataSnapshot.getValue(Etudiant.class);
                        Log.d("GettingTesteDANSDATA", vendeur1+"");
                        if (vendeur1 != null) {// Après récupération.
                            sTestGettingSalesMan = 1; // Infos obtenues.
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        sTestGettingSalesMan = -1; // Infos non obtenues.
                    }
                })
        ;
    }

    public static String acheteurName;

    public static void getBuyerById(String idAcheteur) {

        final Etudiant[] acheteur = {new Etudiant()};
        sReference.child("Etudiants")
                .child(idAcheteur)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        acheteur[0] = dataSnapshot.getValue(Etudiant.class);
                        acheteurName = acheteur[0].getPrenomEtu()+" "+acheteur[0].getNomEtu();
                        Log.d("BuyerName", acheteurName);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        sTestGettingSalesMan = -1; // Infos non obtenues.
                    }
                })
        ;
    }


    public static List<DetailsPrestation> sAllDetails = new ArrayList<>();
    //public static short sTestGettingRetail; // -1: Failure, 0: default, 1: OK;

    /**
     * Permet de récupérer les infos du details.
     */
    public static void getDetailsPrestation() {

        Log.d("JKKKLLL", "HBHJBJB");
        sReference.child("Details Prestations")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        sAllDetails.clear();
                        for (DataSnapshot detailSnapshot : dataSnapshot.getChildren()) {

                            DetailsPrestation details = detailSnapshot.getValue(DetailsPrestation.class);
                            Log.d("JKKKLLL", details.toString());
                            sAllDetails.add(details);
                        }
                        //sTestGettingRetail = 1;
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                        //sTestGettingRetail = -1; // Infos non obtenues.
                    }
                });
    }

    /**
     * Permet de récupérer les infos du details à partir de son identifiant.
     */
    public static DetailsPrestation getDetailById(String idDetail) {

        for (DetailsPrestation details : sAllDetails) {

            if (details.getIdDetail().equals(idDetail)) {

                return details;
            }
        }

        return null;
    }


    //================================ UMAPS ====================================
    private static DatabaseReference sRefUmaps = FirebaseDatabase.getInstance().getReference().child("UMaps");
    public static DatabaseReference sRefLieux = sRefUmaps.child("Lieux");
    private static String sKeyLieu = sRefLieux.push().getKey();
    public static List<Lieu> sLieux = new ArrayList<>();
    public static List<String> sNomLieu = new ArrayList<>();

    /**
     * Permet d'ajouter un lieu à la carte.
     * @param lieu
     */
    public static void ajouterLieux(Lieu lieu) {

        lieu.setIdLieu(sKeyLieu);
        sRefLieux.child(sKeyLieu).setValue(lieu);
    }

    /**
     * Permet d'ajouter plusieurs lieu à la fois.
     * @param lieux
     */
    public static void ajouterLieux(List<Lieu> lieux) {

        Iterator<Lieu> iterator = lieux.iterator();
        while (iterator.hasNext()) {

            String keyLieu = sRefLieux.push().getKey();
            iterator.next().setIdLieu(keyLieu);
        }

        sRefLieux.setValue(lieux);
    }

    /**
     * Permet de récupérer les lieux.
     */
    /*public static void getLieux() {

        sRefLieux.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                sLieux.clear();
                sNomLieu.clear();
                for (DataSnapshot lieu : dataSnapshot.getChildren()) {

                    Lieu lieu1 = lieu.getValue(Lieu.class);
                    sLieux.add(lieu1);
                    sNomLieu.add(lieu1.getPosition());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }*/

    /**
     * Permet d'ajouter le lieu dans la liste des lieux inconnus, et serra ultérieurement ajouter par l'administrateur.
     * @param lieu
     */
    public static void addLieuInconnu(String lieu) {

        DatabaseReference lieuInconnu = sRefUmaps.child("LieuxInconnus");
        lieuInconnu.child(lieu).setValue(lieu);
    }

    // =================================== UService =====================================

    public static DatabaseReference sRefUService = FirebaseDatabase.getInstance().getReference().child("UService");

    /**
     * Permet de mettre à jour le compte de l'utilisateur, utilisé lors d'ouverture d'un comptePro.
     * @param user
     */
    public static void createBosseur(Etudiant user){

        // Dans Etudiant
        sReference.child("Etudiants")
                .child(user.getIdEtu())
                .setValue(user);

        // Dans Uservices
        Bosseur bosseur = new Bosseur(user.getIdEtu(), 0);
        sRefUService.child(user.getIdEtu())
                .setValue(bosseur);
    }
}