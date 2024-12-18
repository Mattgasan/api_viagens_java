package com.api_viagens.service;

import com.api_viagens.model.Location;
import com.api_viagens.repository.LocationRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    // Injeção via construtor
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    // Método para buscar todos os locais
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    // Método para salvar um novo local
    public Location save(Location location) {
        validateLocation(location);
        return locationRepository.save(location);
    }

    // Método para buscar um local por ID
    public Optional<Location> findById(Long id) {
        return locationRepository.findById(id);
    }

    // Método para atualizar um local
    public Location update(Long id, Location location) {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Local com ID " + id + " não encontrado."));

        existingLocation.setName(location.getName());
        existingLocation.setCity(location.getCity());
        existingLocation.setState(location.getState());
        existingLocation.setCountry(location.getCountry());
        validateLocation(existingLocation);

        return locationRepository.save(existingLocation);
    }

    // Método para excluir um local
    public void delete(Long id) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Local com ID " + id + " não encontrado."));
        locationRepository.delete(location);
    }

    // Validações adicionais
    private void validateLocation(Location location) {
        if (location.getName() == null || location.getName().isEmpty()) {
            throw new IllegalArgumentException("O nome do local é obrigatório.");
        }
        if (location.getCity() == null || location.getCity().isEmpty()) {
            throw new IllegalArgumentException("A cidade do local é obrigatória.");
        }
        if (location.getState() == null || location.getState().isEmpty()) {
            throw new IllegalArgumentException("O estado do local é obrigatório.");
        }
        if (location.getCountry() == null || location.getCountry().isEmpty()) {
            throw new IllegalArgumentException("O país do local é obrigatório.");
        }
    }
}
