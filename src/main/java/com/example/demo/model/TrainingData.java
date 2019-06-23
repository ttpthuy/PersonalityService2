package com.example.demo.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
@Getter@Setter
public class TrainingData implements Serializable {
    private int id;
    private String gioiTinh;
    private float diemTN;
    private float diemXH;
    private float chenhlech;
    private float holland;
    private float chuyensau;
    private String ketqua;

    public TrainingData(int id, String gioiTinh, float diemTN, float diemXH, float chenhlech, float holland, float chuyensau, String ketqua) {
        this.id = id;
        this.gioiTinh = gioiTinh;
        this.diemTN = diemTN;
        this.diemXH = diemXH;
        this.chenhlech = chenhlech;
        this.holland = holland;
        this.chuyensau = chuyensau;
        this.ketqua = ketqua;
    }
    public TrainingData(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.gioiTinh = rs.getString("Gioi_tinh");
        this.diemTN = rs.getFloat("Diem_TN");
        this.diemXH = rs.getFloat("Diem_XH");
        this.chenhlech = rs.getFloat("Chenh_lech_nang_luc");
        this.holland = rs.getFloat("Diem_JohnHoland");
        this.chuyensau = rs.getFloat("Diem_chuyen_sau");
        this.ketqua = rs.getString("ket_qua");
    }

    @Override
    public String toString() {
        return "TrainingData{" +
                "id=" + id +
                ", gioiTinh='" + gioiTinh + '\'' +
                ", diemTN=" + diemTN +
                ", diemXH=" + diemXH +
                ", chenhlech=" + chenhlech +
                ", holland=" + holland +
                ", chuyensau=" + chuyensau +
                ", ketqua='" + ketqua + '\'' +
                '}';
    }
}
