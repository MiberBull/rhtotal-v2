package mx.com.axity.commons.to;

import java.math.BigDecimal;

public class TabUserTO {

    public Integer getNumTab() {
        return numTab;
    }

    public void setNumTab(Integer numTab) {
        this.numTab = numTab;
    }

    public String getNameTab() {
        return nameTab;
    }

    public void setNameTab(String nameTab) {
        this.nameTab = nameTab;
    }

    public BigDecimal getPercentageTab() {
        return percentageTab;
    }

    public void setPercentageTab(BigDecimal percentageTab) {
        this.percentageTab = percentageTab;
    }

    private Integer numTab;
    private String nameTab;
    private BigDecimal percentageTab;
}
