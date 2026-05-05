package mx.com.axity.web.rest;

import mx.com.axity.commons.to.SwapRequieredTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("fintech")
public class SolicudFintechController {

    static final Logger LOG = LogManager.getLogger(SolicudFintechController.class);


    @RequestMapping(value = "Fintech/sendFintech", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> genereteSolcitudSwap(@RequestBody SwapRequieredTO swapRequiered) {

        return null;//new ResponseEntity<>(this.IPaysheetSicoFacade.searchEmployee(searchData), HttpStatus.OK);
    }
    }