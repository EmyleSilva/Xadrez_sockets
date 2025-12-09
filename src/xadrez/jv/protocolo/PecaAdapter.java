package xadrez.jv.protocolo;

import com.google.gson.*;
import xadrez.jv.backend.*;
import java.lang.reflect.Type;

public class PecaAdapter implements JsonSerializer<Peca>, JsonDeserializer<Peca> {

    @Override
    public JsonElement serialize(Peca src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject result = new JsonObject();
        // Adiciona uma propriedade "type" para sabermos qual classe é
        result.add("type", new JsonPrimitive(src.getClass().getSimpleName()));
        // Serializa o restante dos dados da peça
        result.add("properties", context.serialize(src, src.getClass())); 
        return result;
    }

    @Override
    public Peca deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        JsonElement element = jsonObject.get("properties");

        try {
            // Tenta descobrir qual classe concreta usar baseado no nome (Bispo, Torre, etc)
            String packageName = "xadrez.jv.backend.";
            return context.deserialize(element, Class.forName(packageName + type));
        } catch (ClassNotFoundException cnfe) {
            throw new JsonParseException("Classe desconhecida: " + type, cnfe);
        }
    }
}