package com.example.genshintodolist.ui;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.genshintodolist.data.Character;
import com.example.genshintodolist.R;
import com.example.genshintodolist.ui.character.CharacterAdapter;

import java.util.ArrayList;
import java.util.List;

public class CharacterFragment extends Fragment {

    private CharacterAdapter adapter;
    private RecyclerView recyclerView;
    private List<Character> characterList;

    public CharacterFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_character, container, false);

        recyclerView = view.findViewById(R.id.recyclerCharacters);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        characterList = getCharacterList();
        adapter = new CharacterAdapter(getContext(), characterList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    private List<Character> getCharacterList(){
        List<Character> list = new ArrayList<>();

        list.add(new Character(R.drawable.albedo_icon, R.raw.albedo, "Albedo"));
        list.add(new Character(R.drawable.alhaitham_icon, R.raw.alhaitham, "Alhaitham"));
        list.add(new Character(R.drawable.amber_icon, R.raw.amber, "Amber"));
        list.add(new Character(R.drawable.arecchino_icon, R.raw.arlecchino, "Arlecchino"));
        list.add(new Character(R.drawable.ayaka_icon, R.raw.ayaka, "Ayaka"));
        list.add(new Character(R.drawable.baizhu_icon, R.raw.baizhu, "Baizhu"));
        list.add(new Character(R.drawable.barbara_icon, R.raw.barbara, "Barbara"));
        list.add(new Character(R.drawable.beidou_icon, R.raw.beidou, "Beidou"));
        list.add(new Character(R.drawable.bennett_icon, R.raw.bennett, "Bennett"));
        list.add(new Character(R.drawable.candace_icon, R.raw.candace, "Candace"));
        list.add(new Character(R.drawable.charlotte_icon, R.raw.charlotte, "Charlotte"));
        list.add(new Character(R.drawable.chevreuse_icon, R.raw.chevreuse, "Chevreuse"));
        list.add(new Character(R.drawable.chiori_icon, R.raw.chiori, "Chiori"));
        list.add(new Character(R.drawable.chongyun_icon, R.raw.chongyun, "Chong Yun"));
        list.add(new Character(R.drawable.clorinde_icon, R.raw.clorinde, "Clorinde"));
        list.add(new Character(R.drawable.collei_icon, R.raw.collei, "Collei"));
        list.add(new Character(R.drawable.cyno_icon, R.raw.cyno, "Cyno"));
        list.add(new Character(R.drawable.dehya_icon, R.raw.dehya, "Dehya"));
        list.add(new Character(R.drawable.diluc_icon, R.raw.diluc, "Diluc"));
        list.add(new Character(R.drawable.diona_icon, R.raw.diona, "Diona"));
        list.add(new Character(R.drawable.dori_icon, R.raw.dori, "Dori"));
        list.add(new Character(R.drawable.eula_icon, R.raw.eula, "Eula"));
        list.add(new Character(R.drawable.faruzan_icon, R.raw.faruzan, "Faruzan"));
        list.add(new Character(R.drawable.fischl_icon, R.raw.fischl, "Fiscl"));
        list.add(new Character(R.drawable.freminet_icon, R.raw.freminet, "Freminet"));
        list.add(new Character(R.drawable.furina_icon, R.raw.furina, "Furina"));
        list.add(new Character(R.drawable.ganyu_icon, R.raw.ganyu, "Ganyu"));
        list.add(new Character(R.drawable.gorou_icon, R.raw.gorou, "Gorou"));
        list.add(new Character(R.drawable.hutao_icon, R.raw.hutao, "Hutao"));
        list.add(new Character(R.drawable.itto_icon, R.raw.itto, "Itto"));
        list.add(new Character(R.drawable.jean_icon, R.raw.jean, "Jean"));
        list.add(new Character(R.drawable.kaeya_icon, R.raw.kaeya, "Keaya"));
        list.add(new Character(R.drawable.kaveh_icon, R.raw.kaveh, "Kaveh"));
        list.add(new Character(R.drawable.kazuha_icon, R.raw.kazuha, "Kazuha"));
        list.add(new Character(R.drawable.keqing_icon, R.raw.keqing, "Keqing"));
        list.add(new Character(R.drawable.kinich_icon, R.raw.kinich, "Kinich"));
        list.add(new Character(R.drawable.kirara_icon, R.raw.kirara, "Kirara"));
        list.add(new Character(R.drawable.klee_icon, R.raw.klee, "Klee"));
        list.add(new Character(R.drawable.kokomi_icon, R.raw.kokomi, "Kokomi"));
        list.add(new Character(R.drawable.kuki_icon, R.raw.kuki_shinobu, "Shinobu"));
        list.add(new Character(R.drawable.layla_icon, R.raw.layla, "Layla"));
        list.add(new Character(R.drawable.lisa_icon, R.raw.lisa, "Lisa"));
        list.add(new Character(R.drawable.lynette_icon, R.raw.lynette, "Lynette"));
        list.add(new Character(R.drawable.lyney_icon, R.raw.lyney, "Lyney"));
        list.add(new Character(R.drawable.miko_icon, R.raw.miko, "Miko"));
        list.add(new Character(R.drawable.mona_icon, R.raw.mona, "Mona"));
        list.add(new Character(R.drawable.mualani_icon, R.raw.mualani, "Mualani"));
        list.add(new Character(R.drawable.nahida_icon, R.raw.nahida, "Nahida"));
        list.add(new Character(R.drawable.navia_icon, R.raw.navia, "Navia"));
        list.add(new Character(R.drawable.neuvillette_icon, R.raw.neuvillette, "Neuvillette"));
        list.add(new Character(R.drawable.nilou_icon, R.raw.nilou, "Nilou"));
        list.add(new Character(R.drawable.ningguang_icon, R.raw.ningguang, "Ningguang"));
        list.add(new Character(R.drawable.noelle_icon, R.raw.noelle, "Noelle"));
        list.add(new Character(R.drawable.qiqi_icon, R.raw.qiqi, "Qiqi"));
        list.add(new Character(R.drawable.raiden_shogun_icon, R.raw.raiden_shogon, "Raiden Shogon"));
        list.add(new Character(R.drawable.razor_icon, R.raw.razor, "Razor"));
        list.add(new Character(R.drawable.rosaria_icon, R.raw.rosaria, "Rosaria"));
        list.add(new Character(R.drawable.sara_icon, R.raw.sara, "Sara"));
        list.add(new Character(R.drawable.sayu_icon, R.raw.sayu, "Sayu"));
        list.add(new Character(R.drawable.sethos_icon, R.raw.sethos, "Sethos"));
        list.add(new Character(R.drawable.shenhe_icon, R.raw.shenhe, "Shenhe"));
        list.add(new Character(R.drawable.sigewinne_icon, R.raw.sigewinne, "Sigewinne"));
        list.add(new Character(R.drawable.sucrose_icon, R.raw.sucrose, "Sucrose"));
        list.add(new Character(R.drawable.tataglia_icon, R.raw.tartaglia, "Tartaglia"));
        list.add(new Character(R.drawable.thoma_icon, R.raw.thoma, "Thoma"));
        list.add(new Character(R.drawable.tighnari_icon, R.raw.tighnari, "Tighnari"));
        list.add(new Character(R.drawable.venti_icon, R.raw.venti, "Venti"));
        list.add(new Character(R.drawable.wanderer_icon, R.raw.wanderer, "Wanderer"));
        list.add(new Character(R.drawable.xiangling_icon, R.raw.xiangling, "Xiangling"));
        list.add(new Character(R.drawable.xianyun_icon, R.raw.xianyun, "XianYun"));
        list.add(new Character(R.drawable.xiao_icon, R.raw.xiao, "Xiao"));
        list.add(new Character(R.drawable.xilonen_icon, R.raw.xilonen, "Xilonen"));
        list.add(new Character(R.drawable.xingqiu_icon, R.raw.xingqiu, "Xingqiu"));
        list.add(new Character(R.drawable.xinyan_icon, R.raw.xinyan, "Xinyan"));
        list.add(new Character(R.drawable.yanfei_icon, R.raw.yanfei, "Yanfei"));
        list.add(new Character(R.drawable.yaoyao_icon, R.raw.yaoayao, "Yaoyao"));
        list.add(new Character(R.drawable.yelan_icon, R.raw.yelan, "Yelan"));
        list.add(new Character(R.drawable.yoimiya_icon, R.raw.yoimiya, "Yoimiya"));
        list.add(new Character(R.drawable.yunjin_icon, R.raw.yunjin, "Yunjin"));
        list.add(new Character(R.drawable.zhongli_icon, R.raw.zhongli, "Zhongli"));
        return list;
    }
}