package rs.raf.domaci_3.services;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rs.raf.domaci_3.model.Vacuum;
import rs.raf.domaci_3.repositories.VacuumRepository;

@Component
public class AsyncComponent {

    private final VacuumRepository vacuumRepository;

    public AsyncComponent(VacuumRepository vacuumRepository) {
        this.vacuumRepository = vacuumRepository;
    }

    @Async
    public void startVacuum(Vacuum vacuum)
    {
    try {
        Thread.sleep(15000);
    }
    catch (Exception ex)
    {
        System.out.println("[Start Error]");
    }
    vacuum.setStatus("on");
    vacuum.setDisabled(false);
    vacuumRepository.save(vacuum);
    }

    @Async
    public void stopVacuum(Vacuum vacuum)
    {
    try {
        Thread.sleep(15000);
    }
    catch(Exception ex)
    {
        System.out.println("[Stop error]");
    }
        vacuum.setStatus("off");
        vacuum.setDisabled(false);
        vacuumRepository.save(vacuum);
    }

    @Async
    public void dischargeVacuum(Vacuum vacuum)
    {
        try
        {
            Thread.sleep(15000);
        }
        catch(Exception ex)
        {
            System.out.println("[Discharging error]");
        }

        vacuum.setStatus("discharging");
        vacuumRepository.save(vacuum);

        try{
            Thread.sleep(15000);
        }
        catch(Exception ex)
        {
            System.out.println("[Discahrging error - second part]");
        }

        vacuum.setStatus("off");
        vacuum.setDisabled(false);
        vacuumRepository.save(vacuum);
    }
}
