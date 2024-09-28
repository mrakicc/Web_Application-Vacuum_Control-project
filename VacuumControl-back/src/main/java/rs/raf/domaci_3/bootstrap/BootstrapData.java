package rs.raf.domaci_3.bootstrap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import rs.raf.domaci_3.model.*;
import rs.raf.domaci_3.repositories.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapData implements CommandLineRunner {

    private final UserRepository userRepository;
    private final VacuumRepository vacuumRepository;
    private final PermissionRepository permissionRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public BootstrapData(UserRepository userRepository, VacuumRepository vacuumRepository, PermissionRepository permissionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.vacuumRepository = vacuumRepository;
        this.permissionRepository = permissionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {

        Permission readPermission = new Permission();
        readPermission.setName("can_read_users");
        this.permissionRepository.save(readPermission);

        Permission createPermission = new Permission();
        createPermission.setName("can_create_users");
        this.permissionRepository.save(createPermission);

        Permission updatePermission = new Permission();
        updatePermission.setName("can_update_users");
        this.permissionRepository.save(updatePermission);

        Permission deletePermission = new Permission();
        deletePermission.setName("can_delete_users");
        this.permissionRepository.save(deletePermission);

        Permission searchVacuumPermission = new Permission();
        searchVacuumPermission.setName("can_search_vacuum");
        this.permissionRepository.save(searchVacuumPermission);

        Permission startVacuumPermission = new Permission();
        startVacuumPermission.setName("can_start_vacuum");
        this.permissionRepository.save(startVacuumPermission);

        Permission stopVacuumPermission = new Permission();
        stopVacuumPermission.setName("can_stop_vacuum");
        this.permissionRepository.save(stopVacuumPermission);

        Permission dischargeVacuumPermission = new Permission();
        dischargeVacuumPermission.setName("can_discharge_vacuum");
        this.permissionRepository.save(dischargeVacuumPermission);

        Permission addVacuumPermission = new Permission();
        addVacuumPermission.setName("can_add_vacuum");
        this.permissionRepository.save(addVacuumPermission);

        Permission removeVacuumPermission = new Permission();
        removeVacuumPermission.setName("can_remove_vacuum");
        this.permissionRepository.save(removeVacuumPermission);

        System.out.println("Loading Data...");


        User re_deUser = new User();
        re_deUser.setName("name1");
        re_deUser.setLast_name("lastname1");
        re_deUser.setEmail("email");
        re_deUser.setPassword(this.passwordEncoder.encode("re_deUser"));
        List<Permission> permissionList = new ArrayList<>();
        permissionList.add(readPermission);
        permissionList.add(updatePermission);
        permissionList.add(searchVacuumPermission);
        permissionList.add(stopVacuumPermission);
        permissionList.add(dischargeVacuumPermission);
        re_deUser.setPermissions(permissionList);
        this.userRepository.save(re_deUser);

        Vacuum vac1 = new Vacuum();
        vac1.setName("Vac1");
        vac1.setStatus("off");
        vac1.setActive(true);
        vac1.setAddedBy(re_deUser);
        vac1.setDateAdded(Date.valueOf("2021-05-05"));
        this.vacuumRepository.save(vac1);

        User cr_deUser = new User();
        cr_deUser.setName("name2");
        cr_deUser.setLast_name("lastname2");
        cr_deUser.setEmail("email2");
        cr_deUser.setPassword(this.passwordEncoder.encode("123"));
        List<Permission> permissionList2 = new ArrayList<>();
        permissionList2.add(createPermission);
        permissionList2.add(deletePermission);
        permissionList2.add(startVacuumPermission);
        permissionList2.add(addVacuumPermission);
        permissionList2.add(removeVacuumPermission);
        cr_deUser.setPermissions(permissionList2);
        this.userRepository.save(cr_deUser);

        Vacuum vac2 = new Vacuum();
        vac2.setName("Vac2");
        vac2.setStatus("off");
        vac2.setActive(true);
        vac2.setAddedBy(cr_deUser);
        vac2.setDateAdded(Date.valueOf("2022-05-05"));
        this.vacuumRepository.save(vac2);

        User user = new User();
        user.setName("name");
        user.setLast_name("lastname");
        user.setEmail("emaill");
        user.setPassword(this.passwordEncoder.encode("pass"));
        List<Permission> permissionList3 = new ArrayList<>();
        user.setPermissions(permissionList3);
        this.userRepository.save(user);


        User user4 = new User();
        user4.setName("name4");
        user4.setLast_name("lastname4");
        user4.setEmail("email4");
        user4.setPassword(this.passwordEncoder.encode("321"));
        List<Permission> permissionList4 = new ArrayList<>();
        permissionList4.add(readPermission);
        permissionList4.add(createPermission);
        permissionList4.add(updatePermission);
        permissionList4.add(deletePermission);
        permissionList4.add(searchVacuumPermission);
        permissionList4.add(startVacuumPermission);
        permissionList4.add(stopVacuumPermission);
        permissionList4.add(dischargeVacuumPermission);
        permissionList4.add(addVacuumPermission);
        permissionList4.add(removeVacuumPermission);
        user4.setPermissions(permissionList4);
        this.userRepository.save(user4);

        System.out.println("Data loaded!");
    }
}
