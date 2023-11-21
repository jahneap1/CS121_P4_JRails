package jrails;

import java.io.*;
import java.util.*;
import java.lang.reflect.*;

public class Model {
    static int count = 0;
    static String className;
    final static String dbName = "./db.txt";
    static Map<Integer, Object> dbMap = new HashMap<>();

    private int id = 0;
    public void setID(int id){
        this.id = id;
    }


    public void save() {
        /* this is an instance of the current model */
        try {
            Field[] fields = this.getClass().getFields();
            className = this.getClass().getName();


            File db = new File(dbName);
            if (!db.isFile() || !db.exists()) {
                db.createNewFile();

                BufferedWriter bw = new BufferedWriter(new FileWriter(dbName));

                StringBuilder fieldNames = new StringBuilder("id");
                for (Field f : Class.forName(className).getFields()) {
                    fieldNames.append(" | ").append(f.getName());
                }
                bw.write(fieldNames.toString());
                bw.newLine();
                bw.close();

            }
            else{
                if(dbMap.keySet().isEmpty()){
                    loadDBMap(this.getClass());
                }
            }

            if (this.id == 0) {
                synchronized (this) {
                    count++;
                    this.id = count;
                }

                dbMap.put(this.id, this);

                synchronized (this){
                    StringBuilder fieldVals = new StringBuilder(String.valueOf(this.id));
                    for (Field f : this.getClass().getFields()) {
                        fieldVals.append(" | ").append(f.get(this));
                    }
                    String t = fieldVals.toString();

                    BufferedWriter bw = new BufferedWriter(new FileWriter(dbName, true));
                    bw.write(t);
                    bw.newLine();
                    bw.close();
                }

            } else {
                // update dbMap
                dbMap.put(this.id, this);

                saveDBMap();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    //save helper methods

    private static Object getFieldValue(Object input, Field f){
        String inType = input.getClass().getSimpleName();
        String type = ((Class) f.getType()).getSimpleName();

        switch (type){
            case "Integer", "int":
                switch (inType){
                    case "Integer":
                        case "int": return input;
                    case "String":
                        if (input.equals("false") || input.toString().isEmpty() || input.toString().equals(" ")){return 0;}
                        if (input.equals("true")){return 1;}
                        try{
                            return Integer.valueOf(input.toString());
                        }catch (Exception e){return 1;}
                    case "boolean":
                    case "Boolean":
                        return (Boolean)input? 1:0;
                    default:
                        throw new IllegalStateException("Unexpected value: " + inType);
                }

            case "String":
                return String.valueOf(input);

            case "Boolean":
            case "boolean":
                switch (inType){
                    case "Integer":
                    case "int":
                            return !(Integer.valueOf(0)).equals(input);
                    case "String":
                        return Boolean.valueOf(input.toString());
                    case "Boolean":
                    case "boolean":
                        return input;
                    default:
                        throw new IllegalStateException("Unexpected value: " + inType);
                }
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    private static void loadDBMap(Class c) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException, IOException {
        try{
            BufferedReader br = new BufferedReader(new FileReader(dbName));
            String line= br.readLine();
            if(line==null || line.isEmpty()){
                br.close();
                BufferedWriter bw = new BufferedWriter(new FileWriter(dbName));

                StringBuilder fieldNames = new StringBuilder("id");
                for (Field f : Class.forName(className).getFields()) {
                    fieldNames.append(" | ").append(f.getName());
                }
                bw.write(fieldNames.toString());
                bw.newLine();
                bw.close();
                return;
            }

            line= br.readLine();

            while(line!=null && !line.isEmpty()){
                String[] fields = line.split(" | ");

                int i=0;
                int id = Integer.parseInt(fields[i++]);
                Object instance = c.getDeclaredConstructor().newInstance();

                c.getMethod("setID", int.class).invoke(instance, id);

                for(Field f:c.getFields()){
                    f.set(instance, getFieldValue(fields[i++], f));
                }

                dbMap.put(id, instance);
                line= br.readLine();
            }
            br.close();
        }
        catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveDBMap() throws IllegalArgumentException, IllegalAccessException, IOException, ClassNotFoundException {
        BufferedWriter bw0 = new BufferedWriter(new FileWriter(dbName));

        StringBuilder fieldNames = new StringBuilder("id");
        for (Field f : Class.forName(className).getFields()) {
            fieldNames.append(" | ").append(f.getName());
            }
            bw0.write(fieldNames.toString());
            bw0.newLine();
            bw0.close();

        BufferedWriter bw = new BufferedWriter(new FileWriter(dbName, true));
        for (Integer id : dbMap.keySet()) {
            StringBuilder fieldVals = new StringBuilder(String.valueOf(id));
            for (Field f : dbMap.get(id).getClass().getFields()) {
                fieldVals.append(" | ").append(f.get(dbMap.get(id)));
            }
            String str = fieldVals.toString();
            bw.write(str);
            bw.newLine();
        }
        bw.close();
    } 

    //save done :)

    public int id() {
        try {
        }catch (Exception e){e.printStackTrace(); return 0;}
        return this.id;
    }
    //id done :)

    public static <T> T find(Class<T> c, int id) {
        try {
            if(dbMap.keySet().isEmpty()){
                loadDBMap(c);
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException | InstantiationException |
        InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }

        if (!dbMap.containsKey(id)) {
            return null;
        }


        try {
            Object dbEntry = dbMap.get(id);
            T instance = c.getDeclaredConstructor().newInstance();

            c.getMethod("setID", int.class).invoke(instance, id);

            for(int i=0; i < c.getFields().length; i++){
                Field f = c.getFields()[i];
                Field dbField = dbEntry.getClass().getFields()[i];
                f.set(instance, getFieldValue(dbField.get(dbEntry), f));
            }
            return instance;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    //find done :)


    public static <T> List<T> all(Class<T> c) {
        try {
            if(dbMap.keySet().isEmpty()){
                loadDBMap(c);
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException | InstantiationException |
        InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }

        List<T> listA = new ArrayList<>();
        for (Integer i : dbMap.keySet()) {
            listA.add(find(c, i));
        }
        return listA;
    }

   //all done :)
   
    public void destroy() {
        try {
            if(dbMap.keySet().isEmpty()){
                loadDBMap(this.getClass());
            }
        } catch (IllegalArgumentException | IllegalAccessException | IOException | InstantiationException |
                InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException e1) {
            e1.printStackTrace();
        }

        if(!dbMap.containsKey(this.id)){
            throw new UnsupportedOperationException("Key not found");
        }

        dbMap.remove(this.id);

        try {
            saveDBMap();
        } catch (IllegalArgumentException | IllegalAccessException | IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //destroy done :)

    public static void reset() {
        try {
            dbMap.clear();
            new PrintWriter(dbName).close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    //reset done :)
}