package com.example.ausias.intercibus;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ausias.intercibus.basedades.BBDDHelper;
import com.example.ausias.intercibus.recyclers.RecyclerCrearEspai;
import com.example.ausias.intercibus.classes.Extra;
import com.example.ausias.intercibus.classes.Espai;
import com.example.ausias.intercibus.classes.JSONParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Aquesta classe serveix per crear un espai
 * @author Antonio de Dios Durán
 * @version 1.0
 */
public class CrearEspai extends BaseActivity {

    Espai espai;

    RecyclerView rv;

    List<Extra> extres = new ArrayList<>();

    // JSON parser class
    JSONParser jsonParser = new JSONParser();

    //ids
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private ImageView imageView;

    private int GALLERY = 1;

    Bitmap bitmapFoto;
    boolean fotoPosada;

    Spinner spinner;

    private static final String IMAGE_DIRECTORY = "/intercibus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_espai);

        imageView = findViewById(R.id.imFoto);

        rv = (RecyclerView) findViewById(R.id.recyclerExtres);
        rv.setLayoutManager(new LinearLayoutManager(this));

        spinner = (Spinner) findViewById(R.id.spinnerProvincia);
        String[] provincies = {"Barcelona","Girona","Tarragona","Lleida"};
        spinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provincies));
        fotoPosada = false;
    }

    /**
     * Mètode que recull el username de la base de dades del SQLite i agafa tota la informació de la vista
     * @param view Paràmetre que fa referència a una vista
     */
    public void onClick(View view) {

        BBDDHelper bd = new BBDDHelper(this);
        List<String> usuari = bd.getUsuari();
        Log.d("username", usuari.get(0));
        //String username = getIntent().getExtras().getString("username");
        String username = usuari.get(0);

        EditText etNomEspai = findViewById(R.id.etNomEspai);
        EditText etDescripcio = findViewById(R.id.textDescripcio);
        EditText etDireccio = findViewById(R.id.etDireccio);
        EditText etArea = findViewById(R.id.etArea);

        EditText etPreu = findViewById(R.id.etPreu);

        String nomEspai = etNomEspai.getText().toString();
        String descripcio = etDescripcio.getText().toString();
        String direccio = etDireccio.getText().toString();
        String areaString = (etArea.getText().toString());
        String preuString = (etPreu.getText().toString());

        String provincia = spinner.getSelectedItem().toString();

        int area = -1;
        float preu = -1;

        boolean totOK = true;

        if (nomEspai.isEmpty() || descripcio.isEmpty() || direccio.isEmpty() || areaString.isEmpty() || preuString.isEmpty()) {
            totOK = false;
            String message = getString(R.string.omplirCampsObligatoris);
            //Toast.makeText(CrearEspai.this, "Has d'omplir tots els camps obligatoris", Toast.LENGTH_LONG).show();
            Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
        }
        else {
            area = Integer.parseInt(areaString);
            preu = Float.parseFloat(preuString);
        }

        //if (bitmapFoto.equals(null)) {
        if (!fotoPosada) {
            totOK = false;
            String message = getString(R.string.introduitFotografia);
            Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
        }

        if (nomEspai.contains(":") || descripcio.contains(":") || direccio.contains(":")) {
            totOK = false;
            String message = getString(R.string.dosPuntosNoPermitido);
            Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
        }

        if (nomEspai.contains("/") || descripcio.contains("/") || direccio.contains("/")) {
            totOK = false;
            String message = getString(R.string.barraNoPermitido);
            Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
        }

        if (totOK) {
            CrearEspaiAsyncTask crearEspai = new CrearEspaiAsyncTask();

            // Dimnesionar foto
            bitmapFoto = getScaledBitmap(bitmapFoto, 650, 550);
            // Creem l'objecte empresa
            espai = new Espai(nomEspai, descripcio, area, direccio, provincia, preu, extres, username, bitmapFoto);
            // Executem l'AsyncTask
            crearEspai.execute(espai);
        }

    }

    /**
     * Mètode per obtenir una fotografia d'una direcció externa
     * @param view Paràmetre que retorna una vista
     */
    public void obreFotos(View view) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == this.RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri contentURI = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), contentURI);
                    String path = saveImage(bitmap);
                    //Toast.makeText(this, "Image Saved!", Toast.LENGTH_SHORT).show();
                    //bitmap = getScaledBitmap(bitmap, 200, 200);
                    imageView.setImageBitmap(bitmap);
                    bitmapFoto = bitmap;
                    fotoPosada = true;

                } catch (IOException e) {
                    e.printStackTrace();
                    //Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    /**
     * Mètedo que permet redimensionar el tamany de la fotografia
     * @param b Paràmetre que fa referència a la fotografia en bits
     * @param reqWidth Paràmetre que fa referència a la llargada de la fotografia
     * @param reqHeight Paràmetre que fa referència a l'altura de la fotografia
     * @return El bitmap de la fotografia modificada
     */
    private Bitmap getScaledBitmap(Bitmap b, int reqWidth, int reqHeight)
    {
        int bWidth = b.getWidth();
        int bHeight = b.getHeight();

        int nWidth = bWidth;
        int nHeight = bHeight;

        if(nWidth > reqWidth)
        {
            int ratio = bWidth / reqWidth;
            if(ratio > 0)
            {
                nWidth = reqWidth;
                nHeight = bHeight / ratio;
            }
        }

        if(nHeight > reqHeight)
        {
            int ratio = bHeight / reqHeight;
            if(ratio > 0)
            {
                nHeight = reqHeight;
                nWidth = bWidth / ratio;
            }
        }

        return Bitmap.createScaledBitmap(b, nWidth, nHeight, true);
    }

    /**
     * Mètode que permet guardar una fotografia en bits en un arxiu
     * @param myBitmap Paràmetre que fa referència a la fotografia en bits
     * @return Un String amb la ruta on s'ha guardat la fotografia
     */
    public String saveImage(Bitmap myBitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File wallpaperDirectory = new File(
                Environment.getExternalStorageDirectory() + IMAGE_DIRECTORY);
        // have the object build the directory structure, if needed.
        if (!wallpaperDirectory.exists()) {
            wallpaperDirectory.mkdirs();
        }

        try {
            File f = new File(wallpaperDirectory, Calendar.getInstance()
                    .getTimeInMillis() + ".jpg");
            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            MediaScannerConnection.scanFile(this,
                    new String[]{f.getPath()},
                    new String[]{"image/jpeg"}, null);
            fo.close();
            //Log.d("TAG", "File Saved::--->" + f.getAbsolutePath());

            return f.getAbsolutePath();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return "";
    }

    /**
     * Mètode que retorna una imatge
     * @param bmp  Paràmetre que fa referència a la fotografia en bits
     * @return Un String de l'imatge
     */
    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private class CrearEspaiAsyncTask extends AsyncTask<Espai, Void, Boolean> {

        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Espai... args) {
            Espai espai = args[0];

            String nom = espai.getNom();
            String descripcio = espai.getDescripcio();
            String area = String.valueOf(espai.getArea());
            String provincia = espai.getProvincia();
            String direccio = espai.getDireccio();
            String preu = String.valueOf(espai.getPreu());
            extres = espai.getExtres();
            String username = espai.getUsername();
            String foto = getStringImage(espai.getBitmap());

            // Building Parameters
            HashMap<String, String> params = new HashMap<String, String>();

            params.put("username", username);
            params.put("descripcio", descripcio);
            params.put("nom", nom);
            params.put("area", area);
            params.put("ubicacio", provincia);
            params.put("direccio", direccio);
            params.put("preu_base", preu);
            params.put("foto", foto);

            // Enviem el número de extres introduits
            params.put("num_extres", Integer.toString(extres.size()));
            // Enviem cada extra al parser
            for (int i = 0; i < extres.size(); i ++) {
                params.put("extra" + Integer.toString(i), extres.get(i).getNom());
                params.put("preu" + Integer.toString(i), Float.toString(extres.get(i).getPreu()));
            }

            // getting JSON Object
            JSONObject json = jsonParser.makeHttpRequest(Constants.REGISTRA_ESPAI_URL,"POST", params);

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    return true;
                } else {
                    return false;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return false;
        }

        protected void onPostExecute(Boolean b) {

            // Segons el resultat del doInBackground, es mostra el resultat obtingut
            if (b) {
                String message = getString(R.string.espaiCreatOK);
                Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CrearEspai.this, PaginaPrincipal.class);
                startActivity(intent);
            } else {
                String message = getString(R.string.espaiCreatKO);
                Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Mètode que permet afegir un extres a l'hora de fer la reserva
     * @param view Paràmetre que fa referència a una vista
     */
    public void afegirExtra(View view) {
        EditText etNomExtra = findViewById(R.id.etNomExtra);
        EditText etPreuExtra = findViewById(R.id.etPreuExtra);

        String nomExtra = etNomExtra.getText().toString();
        String preuExtra = etPreuExtra.getText().toString();

        if (!nomExtra.isEmpty() && !preuExtra.isEmpty()) {
            // Pasem a majúscules per simplicitat a la BBDD
            nomExtra = nomExtra.toLowerCase();
            Extra extra = new Extra (nomExtra, Float.parseFloat(preuExtra));
            extres.add(extra);

            RecyclerCrearEspai adap = new RecyclerCrearEspai(extres);
            rv.setAdapter(adap);

            etNomExtra.setText(null);
            etPreuExtra.setText(null);
        }
        else {
            String message = getString(R.string.nomPreuObligatori);
            Toast.makeText(CrearEspai.this, message, Toast.LENGTH_LONG).show();
        }
    }
}
