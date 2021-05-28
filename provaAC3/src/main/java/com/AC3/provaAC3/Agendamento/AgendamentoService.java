package com.AC3.provaAC3.Agendamento;

import com.AC3.provaAC3.Controle.ProcessoController;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AgendamentoService {

    ProcessoController controller = new ProcessoController();

    @Scheduled(fixedRate = 5000)
    public void servicoAgendado(){
        System.out.println("Executando processos em fila...");
        controller.execProcessos();
    }
}
