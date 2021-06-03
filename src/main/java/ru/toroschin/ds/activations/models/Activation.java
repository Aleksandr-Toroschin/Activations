package ru.toroschin.ds.activations.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "STATISTICHESV")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activation {
    @Column(name = "ID")
    @Id
    private String id;

    @Column(name = "RAGSOC")
    private String name;    // юр. наименование

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "CODCLI")
    private String code;  // для активных показывает код клиента

    @Column(name = "ABILITATO")
    private int active;  // активен или нет. 1 - активен, 0 - нет

    @Column(name = "CODCLIRIF")
    private String dateDisconnect; // дата отключения

    @Column(name = "ULTIMALIC")
    private String dateConnect; // дата активации

    @Column(name = "PRIMAATTIVAZIONE")
    private String dateFirstConnect; // дата первой активации

    @Column(name = "CODATTIVAZIONE")
    private String codeActivation; // код активации

    @Column(name = "NOTEUTENTE")
    private String remark;  // примечание. В нем лежит код дилера, если он отключен

    @Column(name = "DATAEXPORT")
    private String dateDownload; // дата выгрузки этой информации

    @Column(name = "COEFF")
    private String coeff;

//    private Date dateDisconnectNew;
//    private Date dateConnectNew;
}
