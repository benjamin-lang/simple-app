package de.grid.example.adapter.common;

import org.junit.Assert;
import org.junit.Test;

import java.util.UUID;

public class IdGeneratorUUIDTest
{
    @Test
    public void generateId()
    {
        IdGeneratorUUID idGenerator = new IdGeneratorUUID();

        String generatedId = idGenerator.generateId();

        UUID uuid = UUID.fromString(generatedId);

        Assert.assertNotNull(uuid);
    }
}