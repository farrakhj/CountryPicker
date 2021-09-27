package pk.farrakhj.countrypickerlibrary.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import pk.farrakhj.countrypickerlibrary.R;
import pk.farrakhj.countrypickerlibrary.dto.Country;
import pk.farrakhj.countrypickerlibrary.utils.Util;

public class CountryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private ProviderOnItemClickListener providerOnItemClickListener;
    ArrayList<Country> countries;
    ArrayList<Country> countriesFiltered;
    private ItemFilter mFilter = new ItemFilter();

    public CountryAdapter(Context context, ArrayList<Country> countries) {
        this.context = context;
        this.countries = countries;
        this.countriesFiltered = countries;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_country, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Country country = countriesFiltered.get(position);
        ViewHolder viewHolder = (ViewHolder) holder;

        viewHolder.textViewCountryName.setText(Util.isValid(context, country.getName()));
    }

    @Override
    public int getItemCount() {
        return countriesFiltered.size();
    }

    public interface ProviderOnItemClickListener {
        void onProviderClick(Country country);
    }

    public void setProviderOnItemClickListener(ProviderOnItemClickListener providerOnItemClickListener) {
        this.providerOnItemClickListener = providerOnItemClickListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ConstraintLayout constraintLayoutParentView;
        public TextView textViewCountryName;

        public ViewHolder(View itemView) {
            super(itemView);

            constraintLayoutParentView = (ConstraintLayout) itemView.findViewById(R.id.constraintLayoutParentView);
            textViewCountryName = (TextView) itemView.findViewById(R.id.textViewCountryName);


            constraintLayoutParentView.setOnClickListener(onClickListener);
        }

        private View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (providerOnItemClickListener != null) {
                    providerOnItemClickListener.onProviderClick(countriesFiltered.get(getAdapterPosition()));
                }
            }
        };
    }

    public Filter getFilter() {
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                countriesFiltered = countries;

            } else {
                ArrayList<Country> tempCountries = new ArrayList<Country>();

                for (Country row : countries) {
                    if (row.getName().toUpperCase().trim().contains(constraint.toString().toUpperCase().trim())) {
                        tempCountries.add(row);
                    }
                }

                countriesFiltered = tempCountries;
            }

            results.values = countriesFiltered;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint,FilterResults results) {
            countriesFiltered = (ArrayList<Country>) results.values;
            notifyDataSetChanged();
        }
    }
}
