package com.babacar.ucollaboration.UMaps.Utilitaires;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.babacar.ucollaboration.UMaps.Models.Lieu;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.Random;

import static com.babacar.ucollaboration.Globals.DataAccessObject.DataBase.sLieux;


public class UcadCarte extends SupportMapFragment implements OnMapReadyCallback {

    private static final int REQUEST_CODE = 32;
    private static GoogleMap mGoogleMap;


    public UcadCarte() {

        getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mGoogleMap = googleMap;

        LatLng ucad = new LatLng(14.691393650652753, -17.46302403509617);

        mGoogleMap.addMarker(new MarkerOptions()
                .position(ucad)
                .title("Université Cheikh Anta Diop")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ucad, 15));

        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);


        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(latLng);
                markerOptions.title(latLng.latitude+" "+latLng.longitude);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                mGoogleMap.clear();
                mGoogleMap.addMarker(markerOptions);
                mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));
                mGoogleMap.getUiSettings().setMapToolbarEnabled(true);
                aireUcad(); // Méthode pour aficher la zone UCAD.
            }
        });
        aireUcad(); // Méthode pour aficher la zone UCAD.


        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        //mGoogleMap.setMyLocationEnabled(true);
        localisation(); // Méthode permettant de localiser l'utilisateur.
        mGoogleMap.getUiSettings().setScrollGesturesEnabledDuringRotateOrZoom(true);

    }

    /**
     * Permission d'utilisation de la position actuelle de l'utilisateur
     */
    private void localisation() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (mGoogleMap != null) {
                mGoogleMap.setMyLocationEnabled(true);
            }
        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE);
        }
    }

    /**
     * Permet de connaitre la position actuctuelle de l'utilisateur.
     */
//    public static void location(Lieu lieu) {
//
//        LatLng maPosition = new LatLng(lieu.getLat(), lieu.getLong());
//
//        mGoogleMap.addMarker(new MarkerOptions()
//                .position(maPosition)
//                .title(lieu.getPosition())
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
//
//        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(maPosition, 16));
//    }

    /**
     * Permet de chercher un lieu sur la carte.
     * @param lieu
     */
    public static void moveCamera (Lieu lieu) {

        LatLng latLng = new LatLng(lieu.getLat(), lieu.getLong());

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title(lieu.getPosition());
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));

        mGoogleMap.clear();
        mGoogleMap.addMarker(markerOptions);
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17));

        aireUcad(); // Méthode pour aficher la zone UCAD.
    }


    /**
     * Permet d'explorer d'autres lieux pour connaitre d'avantage l'université.
     */
    public static void explorer() {

        if (sLieux.size() != 0) {

            Random random = new Random();
            int position = random.nextInt(sLieux.size());

            if (position > 0)
                moveCamera(sLieux.get(position));
        }
        aireUcad(); // Méthode pour aficher la zone UCAD.
    }

    /**
     * Permet de déliminter la zone de l'UCAD.
     */
    private static void aireUcad() {

        PolygonOptions polygonOptions = new PolygonOptions()
                .strokeColor(Color.RED)
                .add(new LatLng(14.692578044222667, -17.461818382143974))
                .add(new LatLng(14.692832305313065, -17.46186800301075))
                .add(new LatLng(14.693034027549317, -17.462043687701225))
                .add(new LatLng(14.693198778047833, -17.462225072085857))
                .add(new LatLng(14.693269802338596, -17.462323978543278))
                .add(new LatLng(14.69332720551564, -17.46244098991156))
                .add(new LatLng(14.693244506018507, -17.46255900710821))
                .add(new LatLng(14.693032405988678, -17.462737709283825))
                .add(new LatLng(14.692922788461368, -17.46284030377865))
                .add(new LatLng(14.692763875380336, -17.462982460856438))
                .add(new LatLng(14.692541396872743, -17.463239952921867))
                .add(new LatLng(14.692275135853796, -17.46344782412052))
                .add(new LatLng(14.692000442355383, -17.46369358152151))
                .add(new LatLng(14.69148478302561, -17.46412541717291))
                .add(new LatLng(14.690464811773944, -17.465015910565853))
                .add(new LatLng(14.689883961153203, -17.465568780899048))
                .add(new LatLng(14.690356814539173, -17.46596708893776))
                .add(new LatLng(14.690379516665027, -17.466176636517048))
                .add(new LatLng(14.690133036315311, -17.466418035328388))
                .add(new LatLng(14.689817800515556, -17.4666815623641))
                .add(new LatLng(14.690550106849566, -17.46773600578308))
                .add(new LatLng(14.690751506877005, -17.46835056692362))
                .add(new LatLng(14.690930853283069, -17.46866773813963))
                .add(new LatLng(14.689830773191181, -17.4699092656374))
                .add(new LatLng(14.688975223584464, -17.470594234764576))
                .add(new LatLng(14.688067779515485, -17.471391186118126))
                .add(new LatLng(14.687928322087295, -17.471391186118126))
                .add(new LatLng(14.687488219806015, -17.47024018317461))
                .add(new LatLng(14.686907036953846, -17.469620928168297))
                .add(new LatLng(14.686207150642497, -17.469238713383675))
                .add(new LatLng(14.685562721367011, -17.46890913695097))
                .add(new LatLng(14.685385965144189, -17.468871250748634))
                .add(new LatLng(14.685021749577553, -17.468800842761993))
                .add(new LatLng(14.683560016351011, -17.468356266617775))
                .add(new LatLng(14.682845524599626, -17.46846355497837))
                .add(new LatLng(14.682061299917208, -17.468607388436794))
                .add(new LatLng(14.68124950440715, -17.468730099499226))
                .add(new LatLng(14.679872720873972, -17.468523234128952))
                .add(new LatLng(14.679294436772278, -17.468261048197746))
                .add(new LatLng(14.678156675366363, -17.467733323574066))
                .add(new LatLng(14.677854720020015, -17.465955689549446))
                .add(new LatLng(14.677714283073536, -17.464576363563538))
                .add(new LatLng(14.678367168159294, -17.46308069676161))
                .add(new LatLng(14.679458548961014, -17.46165845543146))
                .add(new LatLng(14.679598011791414, -17.460875250399113))
                .add(new LatLng(14.682973633823524, -17.460897713899612))
                .add(new LatLng(14.686090718921042, -17.46090106666088))
                .add(new LatLng(14.68643936525554, -17.460828647017475))
                .add(new LatLng(14.686830497132718, -17.460670731961727))
                .add(new LatLng(14.688461503030254, -17.45972122997045))
                .add(new LatLng(14.689849259252625, -17.458895780146122))
                .add(new LatLng(14.690091848124503, -17.458770051598552))
                .add(new LatLng(14.69030330237605, -17.4587656930089))
                .add(new LatLng(14.691373543148995, -17.460089698433876))
                .add(new LatLng(14.69257836853547, -17.46164806187153))
                .add(new LatLng(14.692588746544942, -17.46177412569523))
                .add(new LatLng(14.692578044222667, -17.461818382143974))
                ;

        mGoogleMap.addPolygon(polygonOptions);
    }
}