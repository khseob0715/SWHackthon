package com.example.vclab.shipcrew.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.vclab.shipcrew.Model.PositionModel;
import com.example.vclab.shipcrew.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Aiden on 2018-08-29.
 */

public class ListNotificationFragment extends Fragment implements View.OnClickListener{


    private View rootView;

    RecyclerView mRecyclerView;
    DatabaseReference mDatabase;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_manual, null);


        mDatabase = FirebaseDatabase.getInstance().getReference();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.framelayout_notification_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecyclerView.setAdapter(new ShareRecyclerAdapter());


        return rootView;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }

    }

    class ShareRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        List<PositionModel> positionModels;

        public ShareRecyclerAdapter() {
            positionModels = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("recipe").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    // 처음 넘어오는 데이터 // ArrayList 값.
                    positionModels.clear();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        PositionModel positionModel = snapshot.getValue(PositionModel.class);

                        positionModels.add(positionModel);
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }

            });
        }

        @Override// 뷰 홀더 생성
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notification, parent, false);

            return new ShareRecyclerAdapter.ItemViewHolder(view);
        }

        public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
            // 해당 position 에 해당하는 데이터 결합
            ((ItemViewHolder) holder).NameText.setText(positionModels.get(position).getName());
            ((ItemViewHolder) holder).DateText.setText(positionModels.get(position).getDate());

            ((ItemViewHolder) holder).CompleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mDatabase.child("userPoint").child(positionModels.get(position).getTS()).removeValue();
                }
            });

            // 이벤트처리 : 생성된 List 중 선택된 목록번호를 Toast로 출력
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(getContext(), String.format("%d 선택 %s", position + 1, lightModels.get(position).SharePixel.get(0)), Toast.LENGTH_LONG).show();
                }

                // lightModels.get(position).pixelColor[lightModels.get(position).index - 1].getG_color(),
            });
        }

        @Override
        public int getItemCount() {
            return positionModels.size();
        }

        public class ItemViewHolder extends RecyclerView.ViewHolder {
            private TextView DateText;
            private TextView NameText;
            private Button CompleteButton;

            public ItemViewHolder(@NonNull View itemView) {
                super(itemView);
                DateText = (TextView) itemView.findViewById(R.id.item_date_text);
                NameText = (TextView) itemView.findViewById(R.id.item_name_text);
                CompleteButton = (Button)itemView.findViewById(R.id.item_button);

            }
        }
    }
}
