//package rs.raf.domaci_3.repositories;
//
//import org.springframework.core.ParameterizedTypeReference;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Repository;
//import org.springframework.web.reactive.function.BodyInserters;
//import org.springframework.web.reactive.function.client.WebClient;
//import rs.raf.domaci_3.model.Vacuum;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
////@Repository("vacuumRepository")
//public class RestVacuumRepository implements VacuumRepository{
//
//    private final WebClient webClient;
//
//    public RestVacuumRepository(WebClient.Builder webClientBuilder) {
//        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api").build();
//    }
//
//    @Override
//    public <S extends Vacuum> S save(S vacuum) {
//        System.out.println(vacuum.getId());
//        if (vacuum.getId() == null) {
//            return this.webClient
//                    .post()
//                    .uri("/vacuums")
//                    .body(BodyInserters.fromValue(vacuum))
//                    .retrieve()
//                    .toEntity(new ParameterizedTypeReference<S>() {})
//                    .block()
//                    .getBody();
//        }
//        else {
//            // update
//            return this.webClient
//                    .put()
//                    .uri("/vacuums/{id}", vacuum.getId())
//                    .body(BodyInserters.fromValue(vacuum))
//                    .retrieve()
//                    .toEntity(new ParameterizedTypeReference<S>() {})
//                    .block()
//                    .getBody();
//        }
//    }
//
//    @Override
//    public <S extends Vacuum> Iterable<S> saveAll(Iterable<S> posts) {
//        List<S> savedPosts = new ArrayList<>();
//        for (S post : posts) {
//            savedPosts.add(this.save(post));
//        }
//        return savedPosts;
//    }
//
//    @Override
//    public Optional<Vacuum> findById(Long id) {
//        ResponseEntity<Vacuum> response = this.webClient
//                .get()
//                .uri("/vacuum/{id}", id)
//                .retrieve()
//                .toEntity(Vacuum.class)
//                .block();
//        if (response == null) {
//            return Optional.empty();
//        }
//
//        return Optional.ofNullable(response.getBody());
//    }
//
//    @Override
//    public boolean existsById(Long id) {
//        return this.findById(id).isPresent();
//    }
//
//    @Override
//    public List<Vacuum> findAll() {
//        ResponseEntity<List<Vacuum>> response = this.webClient
//                .get()
//                .uri("/vacuums/all")
//                .header("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJlbWFpbDQiLCJleHAiOjE3MDY3NzEyNTYsImlhdCI6MTcwNjczNTI1Nn0.YwpJ1VoTBO3nJHY4svHp4uJ2Pl1pbwxdfWptWh80JOKH3Ilp_ToMgyRTM1LvEPbbP8Lz-TAKctUQ7cnTAkva_g")
//                .retrieve()
//                .toEntity(new ParameterizedTypeReference<List<Vacuum
//                        >>() {})
//                .block()
//                ;
//        if (response == null) {
//            return null;
//        }
//
//        return response.getBody();
//    }
//
//    @Override
//    public Iterable<Vacuum> findAllById(Iterable<Long> ids) {
//        List<Vacuum> vacuums = new ArrayList<>();
//        for (Long id : ids) {
//            Optional<Vacuum> vacuumOptional = this.findById(id);
//            if (vacuumOptional.isPresent()) {
//                vacuums.add(vacuumOptional.get());
//            }
//        }
//
//        return vacuums;
//    }
//
//    @Override
//    public long count() {
//        return findAll().size();
//    }
//
//    @Override
//    public void deleteById(Long id) {
//        this.webClient
//                .delete()
//                .uri("/vacuum/{id}", id)
//                .retrieve()
//                .toBodilessEntity()
//                .block();
//    }
//
//    public void deleteByIdAsync(Integer id)
//    {
//        this.webClient
//                .delete()
//                .uri("/posts/{id}", id)
//                .retrieve()
//                .toBodilessEntity()
//                .subscribe((voidResponseEntity -> {
//                    System.out.println(voidResponseEntity.getStatusCode());
//                }));
//        System.out.println("finished deleteByIdAsync(id)");
//    }
//
//    @Override
//    public void delete(Vacuum vacuum) {
//        this.deleteById(vacuum.getId());
//    }
//
//    @Override
//    public void deleteAllById(Iterable<? extends Long> ids) {
//        for (Long id : ids) {
//            this.deleteById(id);
//        }
//    }
//
//    @Override
//    public void deleteAll(Iterable<? extends Vacuum> vacuums) {
//        for (Vacuum vacuum : vacuums) {
//            this.delete(vacuum);
//        }
//    }
//
//    @Override
//    public void deleteAll() {
//        for (Vacuum vacuum: this.findAll()) {
//            this.delete(vacuum);
//        }
//    }
//
//    @Override
//    public Vacuum findByName(String name) {
//        return null;
//    }
//
//    @Override
//    public Vacuum findAllByStatus(Iterable<String> status) {
//        return null;
//    }
//
////    @Override
////    public List<Vacuum> findAllByDateFrom(String from) {
////        return null;
////    }
//
////    @Override
////    public List<Vacuum> findAllByDateTo(Date to) {
////        return null;
////    }
//}
