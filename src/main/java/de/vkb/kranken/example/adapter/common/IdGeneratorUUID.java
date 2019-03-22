package de.vkb.kranken.example.adapter.common;

import de.vkb.kranken.example.application.common.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class IdGeneratorUUID implements IdGenerator
{
    @Override
    public String generateId()
    {
        return UUID.randomUUID().toString();
    }
}
