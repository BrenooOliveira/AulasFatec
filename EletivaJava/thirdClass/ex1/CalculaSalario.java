    package thirdClass.ex1;

    public class CalculaSalario {
        Double salarioBruto;
        Double valeTransp;
        Double impostoRenda;
        private Double inss = 291.24;
        Double deducaoSimplificada = 186.16;
        Double planoSaude = 280.00;
        

        // construtor
        public CalculaSalario(Double salarioBruto) {
            this.salarioBruto = salarioBruto;
            this.valeTransp = salarioBruto * 0.06;
            this.impostoRenda = (salarioBruto * 0.075) - deducaoSimplificada;
        }

        public Double calculaDescontos(){
            Double descontos = valeTransp + impostoRenda + inss + planoSaude;
            return descontos;
        }

        public Double salarioLiquido(){
            return salarioBruto - calculaDescontos();
        }
        
        public Double fgts(){
            return salarioLiquido() * 0.8;
        }

        // -------- FÉRIAS -------- //  
        // terço constitucional de ferias
        public Double tercoConstitucional(){
            return salarioBruto/3;
        }
        // valor bruto das férias
        public Double salarioBrutoFerias(){
            return salarioBruto + tercoConstitucional();
        }
        // valor liquido ferias
        public Double salarioLiquidoFerias(){
            return salarioBrutoFerias() - calculaDescontos();
        }



    }