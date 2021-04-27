package sg.edu.rp.c346.id19020620.p03_classjournal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class infoModule extends AppCompatActivity {

    ListView lv;
    ArrayList<Module> module;
    ArrayAdapter aa;
    Button btnInfo,btnAdd,btnEmail;
    int requestCodeForC347 = 1;
    int requestCodeForC348 = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_module);

        lv = (ListView) this.findViewById(R.id.infoForModules);
        btnInfo = (Button) this.findViewById(R.id.buttonInfo);
        btnAdd = (Button) this.findViewById(R.id.buttonAdd);
        btnEmail = (Button) this.findViewById(R.id.buttonEmail);

        Intent i = getIntent();
        String selectedType = i.getStringExtra("selectedModule");



        module = new ArrayList<>();
        if (selectedType.equals("C347")){

            module.add(new Module("C347",1,"A"));
            module.add(new Module("C347",2,"A+"));
            module.add(new Module("C347",3,"A+"));
            module.add(new Module("C347",4,"A+"));

        }
        else {
            module.add(new Module("C348",1,"F"));
            module.add(new Module("C348",2,"D"));
            module.add(new Module("C348",32,"A+"));
        }

        aa = new moduleAdapter(this,R.layout.rowmoduleinfo,module);
        lv.setAdapter(aa);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent rpIntent = new Intent(Intent.ACTION_VIEW);
                // Set the URL to be used.
                rpIntent.setData(Uri.parse("http://www.rp.edu.sg"));
                startActivity(rpIntent);

            }
        });
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent email = new Intent(Intent.ACTION_SEND);
                // Put essentials like email address, subject & body text
                email.putExtra(Intent.EXTRA_EMAIL,
                        new String[]{"cavenlim03@gmail.com"});
                email.putExtra(Intent.EXTRA_SUBJECT,
                        "Test Email from C347");


                email.putExtra(Intent.EXTRA_TEXT,
                       "Hi Faci , \n  I am ... \n   Please see my remarks so far  .. thank you \n "+getGradeResults(module));
                // This MIME type indicates email
                email.setType("message/rfc822");
                // createChooser shows user a list of app that can handle
                // this MIME type, which is, email
                startActivity(Intent.createChooser(email,
                        "Choose an Email client :"));
            }
        });
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(infoModule.this,addData.class);

                intent.putExtra("arrayCount",module.size()+1);

                startActivityForResult(intent,requestCodeForC347);




            }
        });


    }
    public String getGradeResults(ArrayList<Module> array){
        String sumarry= "";
        for(int i =0 ; i < array.size() ; i++){
            sumarry += "Week "+(i+1)+": DG:"+array.get(i).getModuleGrade()+"\n";
        }
        return sumarry;

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Only handle when 2nd activity closed normally
        //  and data contains something
        if(resultCode == RESULT_OK){
            if (data != null) {
                // Get data passed back from 2nd activity
                String grade = data.getStringExtra("grade");

                // If it is activity started by clicking 				//  Superman, create corresponding String
                if(requestCode == requestCodeForC347){
                   module.add(new Module("c347",module.size()+1,grade));
                   aa.notifyDataSetChanged();
                }
                // If 2nd activity started by clicking
                //  Batman, create a corresponding String



            }
        }
    }

}