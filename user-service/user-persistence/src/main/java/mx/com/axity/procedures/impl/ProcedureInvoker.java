package mx.com.axity.procedures.impl;


import mx.com.axity.commons.to.EmployeesClientProjectTO;
import mx.com.axity.procedures.IProcedureInvoker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import mx.com.axity.commons.to.TabUserTO;
import javax.persistence.EntityManager;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.ParameterMode;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



@Repository
public class ProcedureInvoker  implements  IProcedureInvoker  {

    private final EntityManager entityManager;

    @Autowired
    public ProcedureInvoker(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public  List<TabUserTO> ProcedureTabUser(Long idUser )
    {
        StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("getTabUser");

        storedProcedureQuery.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);

        storedProcedureQuery.setParameter(1, idUser.intValue());

        storedProcedureQuery.execute();

        List<Object[]> results = storedProcedureQuery.getResultList();

       List<TabUserTO> listTab= new ArrayList<>();
        for (var o:results) {
             var list= new TabUserTO();
           list.setNumTab((Integer) o[0]);
           list.setNameTab((String) o[1]);
           list.setPercentageTab((BigDecimal)o[2]);
            listTab.add(list);
        }

        return listTab;
    }

public List<EmployeesClientProjectTO> ProcedureGetEmployeeByIdUser(String email,String curp, String client, String project)
{
    StoredProcedureQuery storedProcedureQuery = entityManager.createStoredProcedureQuery("get_employee");

    storedProcedureQuery.registerStoredProcedureParameter(1, String.class, ParameterMode.IN);
    storedProcedureQuery.registerStoredProcedureParameter(2, String.class, ParameterMode.IN);
    storedProcedureQuery.registerStoredProcedureParameter(3, String.class, ParameterMode.IN);
    storedProcedureQuery.registerStoredProcedureParameter(4, String.class, ParameterMode.IN);
    storedProcedureQuery.setParameter(1, curp);
    storedProcedureQuery.setParameter(2, client);
    storedProcedureQuery.setParameter(3, project);
    storedProcedureQuery.setParameter(4, email);

    storedProcedureQuery.execute();

    List<Object[]> results = storedProcedureQuery.getResultList();

    List<EmployeesClientProjectTO> listEmployee= new ArrayList<>();
    for (var o:results) {
        var list= new EmployeesClientProjectTO();
        list.setId(Long.parseLong((o[0].toString().trim())));
        list.setIdUser(Long.parseLong(o[1].toString().trim()));
        list.setCivilStatus( (String)o[2]);
        list.setName ((String)o[3]);
        list.setLastName ((String)o[4]);
        list.setLastMName ((String)o[5]);
        list.setGender ((String)o[6]);
        if(o[7]!=null) {
            list.setIdClient(Long.parseLong(o[7].toString().trim()));
        }
        list.setClient(o[8].toString().trim());
        if(o[9]!=null) {
            list.setIdProject(Long.parseLong(o[9].toString().trim()));
        }
        list.setIdEmployee (Long.parseLong(o[10].toString().trim()));
        if(o[11]!=null)
        {
            list.setIdSwap (o[11].toString().trim());
        }

        list.setRfc ((String)o[12]);
        list.setCurp ((String)o[13]);
        list.setNss ((String) o[14]);
        list.setEmailClient ((String)o[15]);
        list.setEmail ((String)o[16]);
        list.setPhone( (String)o[17]);
        list.setWorkPermit ((String)o[18]);
        list.setBirthDate ((String)o[19]);
        list.setBirthState ((String)o[20]);
        list.setBirthCountry ((String)o[21]);
        list.setPassportNumber( (String)o[22]);
        list.setNationality ((String)o[23]);
        list.setUserType((String)o[24]);
        list.setWorkPermitConfirm((String)o[25]);
        list.setStatus((String)o[26]);
        list.setLastUserModifier((String)o[27]);
        list.setProject((String)o[28]);
        listEmployee.add(list);
    }

    return listEmployee;
}

}



