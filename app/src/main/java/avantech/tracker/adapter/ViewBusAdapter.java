package avantech.tracker.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;

import avantech.tracker.R;
import avantech.tracker.model.BusModel;

public class ViewBusAdapter extends RecyclerView.Adapter<ViewBusAdapter.MyBusViewHoler> {

    ArrayList<BusModel> busData;
    Context context;

    public ViewBusAdapter(ArrayList<BusModel> busData, Context context) {
        this.busData = busData;
        this.context = context;
    }

    @NotNull
    @Override
    public MyBusViewHoler onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_bus_view,parent,false);
        return new MyBusViewHoler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewBusAdapter.MyBusViewHoler holder, int position) {
        holder.busNumber.setText(busData.get(position).getBusNumber());
        holder.routeNumber.setText(busData.get(position).getBusRouteNumber());
    }

    @Override
    public int getItemCount() {
        return busData.size();
    }

    class MyBusViewHoler extends RecyclerView.ViewHolder {
        TextView busNumber;
        TextView routeNumber;

        public MyBusViewHoler(@NonNull @NotNull View itemView) {
            super(itemView);
            busNumber = itemView.findViewById(R.id.viewBusNumber);
            routeNumber = itemView.findViewById(R.id.viewBusRouteNumber);
        }
    }
}
