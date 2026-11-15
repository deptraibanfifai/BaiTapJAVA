package com.fpt.main;
import com.fpt.model.Pet;
import com.fpt.service.IOService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.ArrayList;
import java.util.List;

public class Test {
    private IOService iOService; // Attribute iOService [cite: 32]

    public void setiOService(IOService iOService) { // Setter để Inject [cite: 36]
        this.iOService = iOService;
    }

    public static void main(String[] args) {
        // Khởi tạo Spring Container
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
        Test testApp = (Test) context.getBean("testBean");

        // Tạo danh sách pet và lưu [cite: 34]
        List<Pet> list = new ArrayList<>();
        list.add(new Pet("Mowzer", "cat", 2));
        testApp.iOService.savePest(list);

        // Lấy danh sách pet và gọi makeNoise [cite: 35]
        List<Pet> savedPets = testApp.iOService.getPets();
        if (savedPets != null) {
            for (Pet p : savedPets) {
                p.makeNoise();
            }
        }
    }
}