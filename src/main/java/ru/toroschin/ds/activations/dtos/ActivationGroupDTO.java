package ru.toroschin.ds.activations.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.toroschin.ds.activations.dtos.ActivationDTO;

import java.util.Date;
import java.util.Objects;

@Data
@NoArgsConstructor
public class ActivationGroupDTO implements Comparable<ActivationGroupDTO> {
    private String name;    // юр. наименование
    private String code;
    private int count;
    private String dateDownload;
    private Date dateDownloadNew;

    public ActivationGroupDTO(ActivationDTO activationDTO) {
        this.name = "";
        this.code = activationDTO.getCode();
        this.count = 0;
        this.dateDownload = activationDTO.getDateDownload();
        this.dateDownloadNew = activationDTO.getDateDownloadNew();
    }

    public ActivationGroupDTO(String code) {
        this.name = "";
        this.code = code;
        this.count = 0;
    }

    public void incrementCount() {
        count ++;
    }

    @Override
    public int compareTo(ActivationGroupDTO activationGroupDTO) {
        return this.code.compareTo(activationGroupDTO.getCode());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActivationGroupDTO that = (ActivationGroupDTO) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }
}
