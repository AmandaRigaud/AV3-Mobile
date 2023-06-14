package com.example.myprevisaotempo;

// Só chamar WeatherCodes.Code_X
//Se precisar de algo mais automatico (Code[0] por exemplo, da pra colocar em um array)

import java.util.HashMap;

public  class WeatherCodes {
    public static final String CODE_0 = "Céu limpo";
    public static final String CODE_1 = "Parcialmente limpo";
    public static final String CODE_2 = "Parcialmente nublado";
    public static final String CODE_3 = "Nublado";
    public static final String CODE_45 = "Nevoéiro";
    public static final String CODE_48 = "Nevoério e neve";
    public static final String CODE_51 = "Garoa leve";
    public static final String CODE_53 = "Garoa moderada";
    public static final String CODE_55 = "Garoa intensa";
    public static final String CODE_56 = "Garoa congelada leve";
    public static final String CODE_57 = "Garoa congelada intensa";
    public static final String CODE_61 = "Chuva leve";
    public static final String CODE_63 = "Chuva moderada";
    public static final String CODE_65 = "Chuva intensa";
    public static final String CODE_66 = "Chuva congelada leve";
    public static final String CODE_67 = "Chuva congelada intensa";
    public static final String CODE_71 = "Neve leve";
    public static final String CODE_73 = "Neve moderada";
    public static final String CODE_75 = "Neve intensa";
    public static final String CODE_77 = "Grãos de neve";
    public static final String CODE_80 = "Pancadas de chuva leves";
    public static final String CODE_81 = "Pancadas de chuva moderadas";
    public static final String CODE_82 = "Pancadas de chuva intensas";
    public static final String CODE_85 = "Aguaceiro de neve leve";
    public static final String CODE_86 = "Aguaceiro de neve intensos";
    public static final String CODE_95 = "Trovoada";
    public static final String CODE_96 = "Granizo leve";
    public static final String CODE_99 = "Granizo intenso";

    public static final HashMap<String, String> codeMap;

    static{
        codeMap = new HashMap<>();
        codeMap.put("CODE_0", "Céu limpo");
        codeMap.put("CODE_1", "Parcialmente limpo");
        codeMap.put("CODE_2", "Parcialmente nublado");
        codeMap.put("CODE_3", "Nublado");
        codeMap.put("CODE_45", "Nevoeiro");
        codeMap.put("CODE_48", "Nevoeiro e neve");
        codeMap.put("CODE_51", "Garoa leve");
        codeMap.put("CODE_53", "Garoa moderada");
        codeMap.put("CODE_55", "Garoa intensa");
        codeMap.put("CODE_56", "Garoa congelada leve");
        codeMap.put("CODE_57", "Garoa congelada intensa");
        codeMap.put("CODE_61", "Chuva leve");
        codeMap.put("CODE_63", "Chuva moderada");
        codeMap.put("CODE_65", "Chuva intensa");
        codeMap.put("CODE_66", "Chuva congelada leve");
        codeMap.put("CODE_67", "Chuva congelada intensa");
        codeMap.put("CODE_71", "Neve leve");
        codeMap.put("CODE_73", "Neve moderada");
        codeMap.put("CODE_75", "Neve intensa");
        codeMap.put("CODE_77", "Grãos de neve");
        codeMap.put("CODE_80", "Pancadas de chuva leves");
        codeMap.put("CODE_81", "Pancadas de chuva moderadas");
        codeMap.put("CODE_82", "Pancadas de chuva intensas");
        codeMap.put("CODE_85", "Aguaceiro de neve leve");
        codeMap.put("CODE_86", "Aguaceiro de neve intensos");
        codeMap.put("CODE_95", "Trovoada");
        codeMap.put("CODE_96", "Granizo leve");
        codeMap.put("CODE_99", "Granizo intenso");
    }

}
