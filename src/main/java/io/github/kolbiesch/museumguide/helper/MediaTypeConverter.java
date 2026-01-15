package io.github.kolbiesch.museumguide.helper;

import jakarta.persistence.AttributeConverter;
import io.github.kolbiesch.museumguide.entities.ExhibitMedia.MediaType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class MediaTypeConverter implements AttributeConverter<MediaType, String> {

    @Override
    public String convertToDatabaseColumn(MediaType mediaType) {
        if (mediaType == null) {
            return null;
        }
        return mediaType.getValue();
    }

    @Override
    public MediaType convertToEntityAttribute(String value) {
        if (value == null) {
            return null;
        }
        return MediaType.fromValue(value);
    }
}
