package com.fpt.service;
import com.fpt.model.Pet;
import java.io.*;
import java.util.List;

public class IOService {
    public void savePest(List<Pet> pets) { // Lưu vào file pets.txt [cite: 29]
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("pets.txt"))) {
            oos.writeObject(pets);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public List<Pet> getPets() { // Đọc từ file pets.txt [cite: 30]
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("pets.txt"))) {
            return (List<Pet>) ois.readObject();
        } catch (Exception e) {
            return null;
        }
    }
}