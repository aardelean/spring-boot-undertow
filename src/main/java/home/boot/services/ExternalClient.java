package home.boot.services;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import home.boot.services.rest.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by alex on 9/26/2015.
 */
@Service
public class ExternalClient {
    @Autowired
    private RestClient restClient;

    public Boolean available(String url) throws SuspendExecution{
        try {
//            restClient.targetWithParams(url).request().get();
            if(new Random().nextInt(5) ==0) {
//                Strand.sleep(2, TimeUnit.SECONDS);
                return false;
            }
        }catch (Exception e){
            return false;
        }
        return true;
    }
}
