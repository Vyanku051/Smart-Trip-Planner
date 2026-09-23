package com.nashik.tripplanner.fragments;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.*;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.*;
import com.nashik.tripplanner.R;
import com.nashik.tripplanner.data.NashikData;
import com.nashik.tripplanner.models.ChatMessage;
import java.text.SimpleDateFormat;
import java.util.*;
public class ChatFragment extends Fragment {
    private RecyclerView rv; private EditText etInput; private View btnSend;
    private final List<ChatMessage> messages=new ArrayList<>();
    private ChatAdapter adapter; private final Handler handler=new Handler(Looper.getMainLooper());
    private static final String[] PROMPTS = {
            "Give me a 1-day plan",
            "Best Misal spots in Nashik?",
            "Hotels near Panchavati",
            "Hotels near Sula Vineyards",
            "Is Nashik safe for solo travelers?",
            "Best places for shopping?",
            "Best time to visit Nashik?",
            "Places to visit with family?",
            "Trekking spots near Nashik",
            "Local food to try in Nashik"
    };
    @Nullable @Override public View onCreateView(@NonNull LayoutInflater i,@Nullable ViewGroup c,@Nullable Bundle s){return i.inflate(R.layout.fragment_chat,c,false);}
    @Override public void onViewCreated(@NonNull View v,@Nullable Bundle s){
        super.onViewCreated(v,s);
        rv=v.findViewById(R.id.rv_chat);etInput=v.findViewById(R.id.et_chat_input);btnSend=v.findViewById(R.id.btn_send);
        if(rv!=null){rv.setLayoutManager(new LinearLayoutManager(requireContext()));adapter=new ChatAdapter(messages);rv.setAdapter(adapter);}
        addAI("Hello! I'm your Nashik AI travel assistant 🌿\n\nI can help plan your Nashik trip — from Trimbakeshwar temples to Sula Vineyards.\n\nWhat would you like to know?");
        if(btnSend!=null)btnSend.setOnClickListener(v2->sendMsg());
        if(etInput!=null)etInput.setOnEditorActionListener((tv,id,ev)->{sendMsg();return true;});
        setupChips(v);
    }
    void setupChips(View v){LinearLayout cont=v.findViewById(R.id.ll_prompt_chips);if(cont==null)return;for(String p:PROMPTS){Button c=new Button(requireContext());c.setText(p);c.setTextSize(11);c.setBackgroundResource(R.drawable.bg_chip_active);try{c.setTextColor(requireContext().getResources().getColor(R.color.primary,null));}catch(Exception ignored){}LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);lp.setMargins(0,0,16,0);c.setLayoutParams(lp);c.setOnClickListener(v2->sendPrompt(p));cont.addView(c);}}
    void sendMsg(){if(etInput==null)return;String t=etInput.getText().toString().trim();if(t.isEmpty())return;etInput.setText("");sendPrompt(t);}
    public void sendPrompt(String text){
        addUser(text);
        ChatMessage typing=ChatMessage.typing();messages.add(typing);if(adapter!=null)adapter.notifyItemInserted(messages.size()-1);scrollBottom();
        handler.postDelayed(()->{int idx=messages.indexOf(typing);if(idx>=0){messages.remove(idx);if(adapter!=null)adapter.notifyItemRemoved(idx);}addAI(NashikData.getAIResponse(text));},(long)(900+Math.random()*600));
    }
    void addUser(String text){String t=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(new Date());messages.add(new ChatMessage(UUID.randomUUID().toString(),text,true,t));if(adapter!=null)adapter.notifyItemInserted(messages.size()-1);scrollBottom();}
    void addAI(String text){String t=new SimpleDateFormat("hh:mm a",Locale.getDefault()).format(new Date());messages.add(new ChatMessage(UUID.randomUUID().toString(),text,false,t));if(adapter!=null)adapter.notifyItemInserted(messages.size()-1);scrollBottom();}
    void scrollBottom(){if(rv!=null&&!messages.isEmpty())rv.scrollToPosition(messages.size()-1);}
    static class ChatAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        private static final int USER=0,AI=1,TYPING=2;
        private final List<ChatMessage> msgs;ChatAdapter(List<ChatMessage> m){msgs=m;}
        @Override public int getItemViewType(int pos){ChatMessage m=msgs.get(pos);if(m.isTyping())return TYPING;return m.isUser()?USER:AI;}
        @NonNull @Override public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup p,int t){LayoutInflater inf=LayoutInflater.from(p.getContext());if(t==USER)return new UVH(inf.inflate(R.layout.item_chat_user,p,false));if(t==TYPING)return new TVH(inf.inflate(R.layout.item_chat_typing,p,false));return new AVH(inf.inflate(R.layout.item_chat_ai,p,false));}
        @Override public void onBindViewHolder(@NonNull RecyclerView.ViewHolder h,int pos){ChatMessage m=msgs.get(pos);if(h instanceof UVH){((UVH)h).tvMsg.setText(m.getMessage());if(((UVH)h).tvTime!=null)((UVH)h).tvTime.setText(m.getTime());}else if(h instanceof AVH){((AVH)h).tvMsg.setText(m.getMessage());if(((AVH)h).tvTime!=null)((AVH)h).tvTime.setText(m.getTime());}}
        @Override public int getItemCount(){return msgs.size();}
        static class UVH extends RecyclerView.ViewHolder{TextView tvMsg,tvTime;UVH(@NonNull View v){super(v);tvMsg=v.findViewById(R.id.tv_chat_message);tvTime=v.findViewById(R.id.tv_chat_time);}}
        static class AVH extends RecyclerView.ViewHolder{TextView tvMsg,tvTime;AVH(@NonNull View v){super(v);tvMsg=v.findViewById(R.id.tv_chat_message);tvTime=v.findViewById(R.id.tv_chat_time);}}
        static class TVH extends RecyclerView.ViewHolder{TVH(@NonNull View v){super(v);}}
    }
}
