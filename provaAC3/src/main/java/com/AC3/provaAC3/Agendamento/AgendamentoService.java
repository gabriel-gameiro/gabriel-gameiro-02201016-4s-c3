package com.AC3.provaAC3.Agendamento;

import org.springframework.scheduling.annotation.Scheduled;

public class AgendamentoService {
    @Scheduled(fixedRate = 3000)
    public void servicoAgendado(){
        System.out.println("Serviço agendado");
    }
}
