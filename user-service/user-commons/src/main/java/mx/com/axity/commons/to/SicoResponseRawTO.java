package mx.com.axity.commons.to;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SicoResponseRawTO implements Serializable {

    @JsonProperty("data")
    private List<SicoResponseTO> data;

    public List<SicoResponseTO> getData() {
        return data;
    }

    public void setData(List<SicoResponseTO> data) {
        this.data = data;
    }
}
