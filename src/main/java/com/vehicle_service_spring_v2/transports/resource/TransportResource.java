package com.vehicle_service_spring_v2.transports.resource;

import com.vehicle_service_spring_v2.transports.TransportServiceI;
import com.vehicle_service_spring_v2.transports.model.dto.TransportDto;
import com.vehicle_service_spring_v2.transports.model.dto.TransportView;
import com.vehicle_service_spring_v2.utils.ViewMapperUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/transports")
@RequiredArgsConstructor
public class TransportResource {
    private final TransportServiceI transportService;
    private final ViewMapperUtil viewMapperUtil;

    @PostMapping
    public ResponseEntity<TransportView> createTransport(@RequestBody TransportDto transportDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Stream.of(transportService.addTransport(transportDto))
                        .map(viewMapperUtil::mapTransportToView)
                        .findFirst()
                        .orElseThrow(
                                () -> new RuntimeException("Something went wrong !")
                        )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransportView> findTransportById(@PathVariable long id) {
        return ResponseEntity.ok(
                Stream.of(transportService.findTransportById(id))
                        .map(viewMapperUtil::mapTransportToView)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Transport with " + id + " not found")));
    }

    @PutMapping
    public ResponseEntity<TransportView> updateTransport(@RequestBody TransportDto transportDto) {
        return ResponseEntity.ok(
                Stream.of(transportService.updateTransport(transportDto))
                        .map(viewMapperUtil::mapTransportToView)
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("Something went wrong!"))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTransportById(@PathVariable long id) {
        transportService.deleteTransportById(id);
        return ResponseEntity.ok("Transport with id " + id + " was deleted");
    }

    @GetMapping
    public ResponseEntity<List<TransportView>> findAllTransports() {
        return ResponseEntity.ok(
                transportService.findAllTransports().stream()
                        .map(viewMapperUtil::mapTransportToView)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/by_brand/{brand}")
    public ResponseEntity<List<TransportView>> findTransportByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(
                transportService.findTransportByBrand(brand).stream()
                        .map(viewMapperUtil::mapTransportToView)
                        .collect(Collectors.toList())
        );
    }

    @GetMapping("/without_driver")
    public ResponseEntity<List<TransportView>> findTransportWithoutDriver() {
        return ResponseEntity.ok(
                transportService.findTransportWithoutDriver().stream()
                        .map(viewMapperUtil::mapTransportToView)
                        .collect(Collectors.toList())
        );
    }

    @PutMapping("/transport_to_route/{transportId}/{routeId}")
    public ResponseEntity<?> addTransportToRoute(@PathVariable long transportId, @PathVariable long routeId) {
        boolean result = transportService.addTransportToRoute(transportId, routeId);

        return result
                ? ResponseEntity.ok("Transport with id " + transportId + " was added to route with id " + routeId)
                : ResponseEntity.badRequest().body("Something went wrong!");
    }

    @DeleteMapping("/transport_from_route/{transportId}/{routeId}")
    public ResponseEntity<?> removeTransportFromRoute(@PathVariable long transportId, @PathVariable long routeId) {
        boolean result = transportService.removeTransportFromRoute(transportId, routeId);

        return result
                ? ResponseEntity.ok("Transport with id " + transportId + " was deleted from route with id " + routeId)
                : ResponseEntity.badRequest().body("Something went wrong !");
    }
}
