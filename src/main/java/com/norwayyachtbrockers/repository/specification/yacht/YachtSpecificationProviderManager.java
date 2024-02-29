package com.norwayyachtbrockers.repository.specification.yacht;

import com.norwayyachtbrockers.model.Yacht;
import com.norwayyachtbrockers.repository.specification.SpecificationProvider;
import com.norwayyachtbrockers.repository.specification.SpecificationProviderManager;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class YachtSpecificationProviderManager implements SpecificationProviderManager<Yacht> {
    private final List<SpecificationProvider<Yacht>> bookSpecificationProviders;

    @Override
    public SpecificationProvider<Yacht> getSpecificationProvider(String key) {
        return bookSpecificationProviders.stream()
                .filter(provider -> provider.getKey().equals(key))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(
                        "Can't find any correct specification provider for the key " + key
                ));
    }
}
