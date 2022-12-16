package pl.edu.pw.mini.projektZPOIF.Services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.edu.pw.mini.projektZPOIF.DTO.CommandDTO;
import pl.edu.pw.mini.projektZPOIF.Repositories.Bulb;
import pl.edu.pw.mini.projektZPOIF.Repositories.BulbRepository;

import java.io.IOException;
import java.io.OutputStream;

@Service
public class TcpService {

    final BulbRepository bulbRepository;

    @Autowired
    public TcpService(BulbRepository bulbRepository) {
        this.bulbRepository = bulbRepository;
    }

    public void connectToBulbs() {
        //pootwierac TCP
        bulbRepository.addBulb(new Bulb());
        for (Bulb bulb : bulbRepository.getBulbList()) {
            bulb.connect();
        }
    }

    public void setPower(Bulb bulb, Boolean on) throws IOException {
        //setPower
        //TCPDTO tcpdto = new TCPDTO(1,"set_power",new Object[]{"abc"});
        //
        //        ObjectMapper objectMapper = new ObjectMapper();
        //
        //        String json = objectMapper.writeValueAsString(tcpdto);
        final var commandDTO = new CommandDTO(
            1,
            "set_power",
            "off",
            "smooth",
            500);
        ObjectMapper objectMapper = new ObjectMapper();
        var json = objectMapper.writeValueAsBytes(commandDTO);

        OutputStream out = bulb.getSocket().getOutputStream();
        out.write(json);
    }

    public void setPower(Boolean on) throws IOException {
        setPower(bulbRepository.getBulbList().get(0), true);
    }
}
