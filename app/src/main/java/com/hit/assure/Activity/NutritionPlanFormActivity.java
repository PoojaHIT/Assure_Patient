package com.hit.assure.Activity;

import static com.hit.assure.Retrofit.ServerCode.CENTRE;
import static com.hit.assure.Retrofit.ServerCode.NUTRITION_CONSULT_FORM;
import static com.hit.assure.Retrofit.ServerCode.SKIN_CONSULT_FORM;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.hit.assure.Activity.VirtualConsult.VirtualConsultantlistActivity;
import com.hit.assure.Model.Centre.CentreData;
import com.hit.assure.Model.Centre.CentreResponse;
import com.hit.assure.Model.NutrituonHelplist;
import com.hit.assure.Model.PatientDetailsFrom.PatientDetailsResponse;
import com.hit.assure.R;
import com.hit.assure.Retrofit.Requestor;
import com.hit.assure.Retrofit.ServerResponse;
import com.hit.assure.Util.Constant;
import com.hit.assure.Util.PreferenceServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NutritionPlanFormActivity extends AppCompatActivity implements View.OnClickListener, ServerResponse {

    private Spinner sp_category, sp_subcat, sp_internet, sp_centre;
    private ToggleButton toogle_skin_concern, toogle_medication;
    private EditText edt_firstname, edt_lastname, editMail, editMobile,
            edtxt_age, edtxt_height, edtxt_weight, edtxt_specific_goals;
    private TextView txt_submit;
    private ArrayList<NutrituonHelplist> employees = new ArrayList<>();
    private String cat;
    private ProgressDialog progressDialog;
    private MultiAdapter adapter;
    private RecyclerView recycler_nutritionist_help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrition_plan_form);
        PreferenceServices.init(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        init();
    }


    @Override
    protected void attachBaseContext(Context newBase) {

        final Configuration override = new Configuration(newBase.getResources().getConfiguration());
        override.fontScale = 1.0f;
        applyOverrideConfiguration(override);

        super.attachBaseContext(newBase);
    }


    public void init() {

        edtxt_height = findViewById(R.id.edtxt_height);
        edtxt_weight = findViewById(R.id.edtxt_weight);
        edtxt_specific_goals = findViewById(R.id.edtxt_specific_goals);

        recycler_nutritionist_help = findViewById(R.id.recycler_nutritionist_help);

        NutrituonHelplist nutrituonHelplist = new NutrituonHelplist("For optimum hair and skin health");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Lose weight");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Deal with a medical condition");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Gain weight /Muscle Building");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Help with an eating disorder");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("improve athletic perfomance");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Improve and maintain health");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("PCOD");
        employees.add(nutrituonHelplist);
        nutrituonHelplist = new NutrituonHelplist("Recovery from surgery");
        employees.add(nutrituonHelplist);

        recycler_nutritionist_help.setLayoutManager(new LinearLayoutManager(this));
        recycler_nutritionist_help.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new MultiAdapter(this, employees);
        recycler_nutritionist_help.setAdapter(adapter);

        adapter.notifyDataSetChanged();


        sp_centre = findViewById(R.id.sp_centre);
        sp_category = findViewById(R.id.sp_category);
        sp_subcat = findViewById(R.id.sp_subcat);
        sp_internet = findViewById(R.id.sp_internet);
        edt_firstname = findViewById(R.id.edt_firstname);
        edt_lastname = findViewById(R.id.edt_lastname);
        editMail = findViewById(R.id.editMail);
        editMobile = findViewById(R.id.editMobile);
        edtxt_age = findViewById(R.id.edtxt_age);
        txt_submit = findViewById(R.id.txt_submit);
        txt_submit.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        //switch (v.getId()) {
            if(v.getId()== R.id.txt_submit){
                if (edt_firstname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your first name", Toast.LENGTH_SHORT).show();
                } else if (edt_lastname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your last name", Toast.LENGTH_SHORT).show();
                } else if (editMobile.getText().toString().length() != 10) {
                    Toast.makeText(this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (editMobile.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your mobile number", Toast.LENGTH_SHORT).show();
                } else if (!Constant.isValidEmail(editMail.getText().toString())) {
                    editMail.requestFocus();
                    Toast.makeText(this, "Enter Valid Email Id", Toast.LENGTH_SHORT).show();
                } else if (editMail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your email", Toast.LENGTH_SHORT).show();
                } else if (edtxt_age.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your age", Toast.LENGTH_SHORT).show();
                } else if (edtxt_height.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your height", Toast.LENGTH_SHORT).show();
                } else if (edtxt_weight.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your weight", Toast.LENGTH_SHORT).show();
                } else if (edtxt_specific_goals.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Enter your specific goals", Toast.LENGTH_SHORT).show();
                } else if (adapter.getSelected().size() < 0) {
                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
                } else {
                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < adapter.getSelected().size(); i++) {
                        stringBuilder.append(adapter.getSelected().get(i).getName());
                        stringBuilder.append(",");
//                        stringBuilder.append("\n");
                    }
                    Toast.makeText(this, "" + stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();

                    showProgressDialog();
                    new Requestor(NUTRITION_CONSULT_FORM, NutritionPlanFormActivity.this).getNutritionConsultantPatientDetails(
                            PreferenceServices.getInstance().getUser_id(),
                            edt_firstname.getText().toString().trim(),
                            edt_lastname.getText().toString().trim(),
                            editMobile.getText().toString().trim(),
                            editMail.getText().toString().trim(),
                            edtxt_age.getText().toString().trim(),
                            edtxt_height.getText().toString().trim(),
                            edtxt_weight.getText().toString().trim(),
                            edtxt_specific_goals.getText().toString().trim(),
                            stringBuilder.toString().trim()
                    );

                }


//                if (adapter.getSelected().size() > 0) {
//                    StringBuilder stringBuilder = new StringBuilder();
//                    for (int i = 0; i < adapter.getSelected().size(); i++) {
//                        stringBuilder.append(adapter.getSelected().get(i).getName());
//                        stringBuilder.append("\n");
//                    }
//                    Toast.makeText(this, "" + stringBuilder.toString().trim(), Toast.LENGTH_SHORT).show();
//                } else {
//                    Toast.makeText(this, "No Selection", Toast.LENGTH_SHORT).show();
//                }


        }
    }


    @Override
    public void success(Object o, int code) {
        switch (code) {
            case NUTRITION_CONSULT_FORM:
                PatientDetailsResponse patientDetailsResponse = (PatientDetailsResponse) o;
                if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("200")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    PreferenceServices.getInstance().setNutrition_consultation_application_form(patientDetailsResponse.getApplication_form());
                    if (!PreferenceServices.getInstance().getCategoryID().isEmpty()) {
                        startActivity(new Intent(this, VirtualConsultantlistActivity.class)
                                .putExtra("cat", PreferenceServices.getInstance().getCategoryID())
                                .putExtra("onlyVI", "0"));
                        finish();
                    }
                } else if (patientDetailsResponse.getResponseCode().equalsIgnoreCase("400")) {
                    hideProgressDialog();
                    Toast.makeText(this, "" + patientDetailsResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public void error(Object o, int code) {

    }

    private void showProgressDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
            progressDialog.setContentView(R.layout.item_loader);
            Objects.requireNonNull(progressDialog.getWindow()).setBackgroundDrawableResource(android.R.color.transparent);
        }
    }

    private void hideProgressDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    public class MultiAdapter extends RecyclerView.Adapter<MultiAdapter.MultiViewHolder> {

        private Context context;
        private ArrayList<NutrituonHelplist> employees;

        public MultiAdapter(Context context, ArrayList<NutrituonHelplist> employees) {
            this.context = context;
            this.employees = employees;
        }

        public void setEmployees(ArrayList<NutrituonHelplist> employees) {
            this.employees = new ArrayList<>();
            this.employees = employees;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public MultiViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.row_nutrition_help_list, viewGroup, false);
            return new MultiViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MultiViewHolder multiViewHolder, int position) {
            multiViewHolder.bind(employees.get(position));
        }

        @Override
        public int getItemCount() {
            return employees.size();
        }

        class MultiViewHolder extends RecyclerView.ViewHolder {

            private TextView textView;
            private ImageView imageView;

            MultiViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(R.id.textView);
                imageView = itemView.findViewById(R.id.imageView);
            }

            void bind(final NutrituonHelplist nnutrituonHelplist) {
                textView.setText(nnutrituonHelplist.getName());

                if (nnutrituonHelplist.isChecked()) {
                    imageView.setBackgroundResource(R.drawable.icon_check);
                } else {
                    imageView.setBackgroundResource(R.drawable.icon_uncheck);
                }

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        nnutrituonHelplist.setChecked(!nnutrituonHelplist.isChecked());
                        if (nnutrituonHelplist.isChecked()) {
                            imageView.setBackgroundResource(R.drawable.icon_check);
                        } else {
                            imageView.setBackgroundResource(R.drawable.icon_uncheck);
                        }
                    }
                });
            }
        }

        public ArrayList<NutrituonHelplist> getAll() {
            return employees;
        }

        public ArrayList<NutrituonHelplist> getSelected() {
            ArrayList<NutrituonHelplist> selected = new ArrayList<>();
            for (int i = 0; i < employees.size(); i++) {
                if (employees.get(i).isChecked()) {
                    selected.add(employees.get(i));
                }
            }
            return selected;
        }
    }
}