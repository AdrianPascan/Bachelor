package ro.ubb.server.repository;

import lombok.*;
import ro.ubb.server.model.CountyServer;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class CountyRepository {
    private List<CountyServer> countyServers = new ArrayList<>();

    public CountyServer addCountyServer(CountyServer countyServer) {
        countyServers.forEach(cs -> {
            if (cs.getName().equals(countyServer.getName())) {
                throw new IllegalArgumentException(
                        String.format("County with name='%s' already exists", countyServer.getName()));
            }
            if (cs.getCountyId().equals(countyServer.getCountyId())) {
                throw new IllegalArgumentException(
                        String.format("County with id=%d already exists", countyServer.getCountyId()));
            }
        });
        countyServers.add(countyServer);
        return countyServer;
    }

    public CountyServer findCountyServer(Integer countyId) {
        return countyServers.stream()
                .filter(cs -> cs.getCountyId().equals(countyId))
                .findFirst()
                .get();
    }
}
