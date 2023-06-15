package com.example.myprevisaotempo;

import java.util.HashMap;

public  class WeatherCodes {

    private static final HashMap<String, String> codeMap;

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

    public String getCode(String code){
        return codeMap.get(code);
    }

}
