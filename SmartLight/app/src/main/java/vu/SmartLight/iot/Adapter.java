package vu.SmartLight.iot;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;



public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private String[] sTitles;
    private String[] sDesc;



    Adapter(Context context, String[] titles,String[] descs){
        this.layoutInflater = LayoutInflater.from(context);
        this.sTitles = titles;
        this.sDesc = descs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.custom_view,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        // bind the textview with data received
        String title = sTitles[i];
        String desc = sDesc[i];
        viewHolder.textTitle.setText(title);
        viewHolder.textDesc.setText(desc);


    }

    @Override
    public int getItemCount() {
        return sTitles.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView textTitle,textDesc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(v.getContext(),Details.class);
                    i.putExtra("Title of device",sTitles[getAdapterPosition()]);
                    i.putExtra("Description of device",sDesc[getAdapterPosition()]);
                    v.getContext().startActivity(i);
                }
            });
            textTitle = itemView.findViewById(R.id.textTitle);
            textDesc = itemView.findViewById(R.id.textDesc);
        }
    }
}
