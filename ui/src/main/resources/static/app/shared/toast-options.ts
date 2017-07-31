import { ToastOptions } from 'ng2-toastr';

export class GlobalToastOptions extends ToastOptions {
  showCloseButton = true;
  animate = 'fade';
  positionClass = 'toast-top-center';
  messageClass = 'global-toast__message';
  titleClass = 'global-toast__title';
}