switch (opzione) {
        case 0 -> {}
        case 1 -> {
        System.out.print("Inserire cognome autore: ");
String input = scanner.nextLine();
                    System.out.println(catalogo.cercaPerAutore(input));
        }
        case 2 -> {
        System.out.print("Inserire titolo: ");
String input = scanner.nextLine();
                    System.out.println(catalogo.cercaPerTitolo(input));
        }
        case 3 -> {
        System.out.print("Inserire anno di pubblicazione: ");
int input = scanner.nextInt();
                    scanner.nextLine();
                    System.out.println(catalogo.filtraPerAnno(input));
        }
default -> System.out.println("Opzione non valida.");
            }