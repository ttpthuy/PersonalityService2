package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;
@Getter@Setter@NoArgsConstructor
public class User {
    private LinkedHashMap<String, Float> iDiemTN;
    private LinkedHashMap<String, Float> iDiemXH;
    private LinkedHashMap<String, Float> iJohnHolland;
    private LinkedHashMap<String, Float> iDiemCHChuyenSau;

    public User(LinkedHashMap<String, Float> iDiemTN, LinkedHashMap<String, Float> iDiemXH, LinkedHashMap<String, Float> iJohnHolland, LinkedHashMap<String, Float> iDiemCHChuyenSau) {
        this.iDiemTN = iDiemTN;
        this.iDiemXH = iDiemXH;
        this.iJohnHolland = iJohnHolland;
        this.iDiemCHChuyenSau = iDiemCHChuyenSau;
    }

    @Override
    public String toString() {
        return "User{" +
                "iDiemTN=" + iDiemTN +
                ", iDiemXH=" + iDiemXH +
                ", iJohnHolland=" + iJohnHolland +
                ", iDiemCHChuyenSau=" + iDiemCHChuyenSau +
                '}';
    }
}
